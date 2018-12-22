package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.Result
import com.example.domain.task.error.FindNearbyPOIsError
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.impl.FindNearbyPOIsTask
import com.example.domain.task.impl.FindPlaceDetailsTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewDataSideEffect
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.model.UISimplePlace
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class POIsActionsExecutor @Inject constructor(
    poisViewModel: POIsViewModel,
    private val findNearbyPOIsTask: FindNearbyPOIsTask,
    private val findPlaceDetailsTask: FindPlaceDetailsTask
) : RxActionsExecutor.HostUnaware<POIsState, POIsViewModel>(poisViewModel), POIsActions {

    override fun findPOIsNearby(latLng: LatLng) {
        mutateState {
            it.copy(pois = ViewDataState.Loading)
        }

        findNearbyPOIsTask.executeWithInput(
            input = latLng,
            onErrorReturn = { Result.Error(FindNearbyPOIsError.Exception(it)) }
        ).mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, result ->
            when (result) {
                is Result.Value -> lastState.copy(
                    pois = ViewDataState.Value(result.value.map {
                        UISimplePlace.fromDomainWithUserLatLng(it, latLng)
                    })
                )
                is Result.Error -> lastState.copy(
                    pois = ViewDataState.Error(result.error)
                )
            }
        })
    }

    override fun findPlaceDetails(place: UISimplePlace, sideEffect: ViewDataSideEffect<Place, FindPlaceDetailsError>) {
        sideEffect.onLoading()

        findPlaceDetailsTask.executeWithInput(
            input = place.domain,
            onErrorReturn = { Result.Error(FindPlaceDetailsError.Exception(it)) }
        ).subscribeWithSideEffect(sideEffect.consumer)
    }
}