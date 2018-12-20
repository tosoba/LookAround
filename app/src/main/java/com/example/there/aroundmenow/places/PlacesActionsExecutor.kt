package com.example.there.aroundmenow.places

import com.example.domain.repo.Result
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.example.domain.task.impl.ReverseGeocodeLocationTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.main.MainViewModel
import com.example.there.aroundmenow.model.UIGeocodingInfo
import javax.inject.Inject

class PlacesActionsExecutor @Inject constructor(
    placesViewModel: PlacesViewModel,
    mainViewModel: MainViewModel,
    private val reverseGeocodeLocationTask: ReverseGeocodeLocationTask
) : RxActionsExecutor.HostAware<PlacesState, MainState, PlacesViewModel, MainViewModel>(placesViewModel, mainViewModel),
    PlacesActions {

    override fun reverseGeocodeLocation() {
        val lastUserLatLng = lastHostState.userLatLng
        when (lastUserLatLng) {
            is ViewData.Value -> {
                mutateState {
                    it.copy(lastGeocodingResult = ViewData.Loading)
                }

                reverseGeocodeLocationTask.executeWithInput(
                    input = lastUserLatLng.value,
                    onErrorReturn = { Result.Error(ReverseGeocodeLocationError.Exception(it)) }
                ).mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, result ->
                    when (result) {
                        is Result.Value -> lastState.copy(
                            lastGeocodingResult = ViewData.Value(UIGeocodingInfo.fromDomain(result.value))
                        )
                        is Result.Error -> lastState.copy(
                            lastGeocodingResult = ViewData.Error(result.error)
                        )
                    }
                })
            }
            else -> {
                mutateState {
                    it.copy(
                        lastGeocodingResult = ViewData.Error(ReverseGeocodeLocationError.UserLocationUnknown)
                    )
                }
            }
        }
    }
}