package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.Result
import com.example.domain.task.error.FindNearbyPOIsError
import com.example.domain.task.impl.FindNearbyPOIsTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.model.UISimplePOI
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class POIsActionsExecutor @Inject constructor(
    poisViewModel: POIsViewModel,
    private val findNearbyPOIsTask: FindNearbyPOIsTask
) : RxActionsExecutor.HostUnaware<POIsState, POIsViewModel>(poisViewModel), POIsActions {

    override fun findPOIsNearby(latLng: LatLng) {
        mutateState {
            it.copy(pois = ViewData.Loading)
        }

        findNearbyPOIsTask.executeWithInput(
            input = latLng,
            onErrorReturn = { Result.Error(FindNearbyPOIsError.Exception(it)) }
        ).mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, result ->
            when (result) {
                is Result.Value -> lastState.copy(
                    pois = ViewData.Value(result.value.map {
                        UISimplePOI.fromDomainWithUserLatLng(it, latLng)
                    })
                )
                is Result.Error -> lastState.copy(
                    pois = ViewData.Error(result.error)
                )
            }
        })
    }
}