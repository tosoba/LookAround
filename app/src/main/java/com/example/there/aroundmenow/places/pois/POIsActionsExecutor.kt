package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.Result
import com.example.domain.task.error.FindNearbyPOIsError
import com.example.domain.task.impl.FindNearbyPOIsTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class POIsActionsExecutor @Inject constructor(
    poisViewModel: POIsViewModel,
    private val findNearbyPOIsTask: FindNearbyPOIsTask
) : RxActionsExecutor<POIsState, POIsViewModel>(poisViewModel), POIsActions {

    override fun findPOIsNearby(latLng: LatLng) {
        mutateState {
            it.copy(pois = ViewData.Loading)
        }

        findNearbyPOIsTask.executeWithInput(
            input = latLng,
            onErrorReturn = { Result.Error(FindNearbyPOIsError.Exception(it)) }
        ).mapToStateThenSubscribeAndDisposeWithViewModel({ _, result ->
            when (result) {
                is Result.Value -> POIsState(ViewData.Value(result.value))
                is Result.Error -> POIsState(ViewData.Error(result.error))
            }
        })
    }
}