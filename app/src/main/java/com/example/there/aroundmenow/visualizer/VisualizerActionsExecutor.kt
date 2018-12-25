package com.example.there.aroundmenow.visualizer

import com.example.domain.repo.Result
import com.example.domain.task.error.FindNearbyPlacesError
import com.example.domain.task.impl.FindNearbyPlacesOfTypeTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.main.MainViewModel
import com.example.there.aroundmenow.model.UIPlaceType
import com.example.there.aroundmenow.model.UISimplePlace
import javax.inject.Inject


class VisualizerActionsExecutor @Inject constructor(
    viewModel: VisualizerViewModel,
    mainViewModel: MainViewModel,
    private val findNearbyPlacesOfTypeTask: FindNearbyPlacesOfTypeTask
) : RxActionsExecutor.HostAware<VisualizerState, MainState, VisualizerViewModel, MainViewModel>(
    viewModel,
    mainViewModel
), VisualizerActions {

    override fun findNearbyPlacesOfType(placeType: UIPlaceType) {
        val lastUserLatLng = lastHostState.userLatLng
        when (lastUserLatLng) {
            is ViewDataState.Value -> {
                mutateState {
                    it.copy(places = ViewDataState.Loading)
                }

                findNearbyPlacesOfTypeTask.executeWithEventArgs(
                    placeType,
                    mapEventArgsToInput = {
                        FindNearbyPlacesOfTypeTask.Input(lastUserLatLng.value, it.query)
                    },
                    onErrorReturn = { Result.Error(FindNearbyPlacesError.Exception(it)) }
                ).mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, result ->
                    when (result) {
                        is Result.Value -> lastState.copy(
                            places = ViewDataState.Value(result.value.map {
                                UISimplePlace.fromDomainWithUserLatLng(it, lastUserLatLng.value)
                            })
                        )
                        is Result.Error -> lastState.copy(
                            places = ViewDataState.Error(result.error)
                        )
                    }
                })
            }
            else -> mutateState {
                it.copy(
                    places = ViewDataState.Error(FindNearbyPlacesError.UserLocationUnknown)
                )
            }
        }
    }

    override fun setPlaces(places: List<UISimplePlace>) {
        mutateState {
            it.copy(places = ViewDataState.Value(places))
        }
    }
}