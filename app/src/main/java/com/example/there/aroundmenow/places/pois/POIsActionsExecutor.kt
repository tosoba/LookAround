package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.Result
import com.example.domain.task.error.FindNearbyPlacesError
import com.example.domain.task.impl.FindNearbyPOIsTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.model.UISimplePlace
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class POIsActionsExecutor @Inject constructor(
    poisViewModel: POIsViewModel,
    private val findNearbyPOIsTask: FindNearbyPOIsTask
) : RxActionsExecutor.HostUnaware<POIsState, POIsViewModel>(poisViewModel), POIsActions {

    override fun findPOIsNearby(latLng: LatLng) {
        mutateState {
            it.copy(pois = ViewDataState.Loading)
        }

        findNearbyPOIsTask.executeWithInput(
            input = latLng,
            onErrorReturn = { Result.Error(FindNearbyPlacesError.Exception(it)) }
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
}