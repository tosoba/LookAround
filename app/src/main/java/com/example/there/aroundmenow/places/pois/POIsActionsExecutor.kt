package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.Result
import com.example.domain.task.impl.FindNearbyPOIsTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class POIsActionsExecutor @Inject constructor(
    poIsViewModel: POIsViewModel,
    private val findNearbyPOIsTask: FindNearbyPOIsTask
) : RxActionsExecutor<POIsState, POIsViewModel>(poIsViewModel), POIsActions {

    override fun findPOIsNearby(latLng: LatLng) {
        mutateState {
            it.copy(pois = ViewData.Loading)
        }

        findNearbyPOIsTask.executeWithInput(latLng)
            .mapToStateThenSubscribeAndDisposeWithViewModel({ _, nearbyPOIsData ->
                when (nearbyPOIsData) {
                    is Result.Value -> POIsState(ViewData.Value(nearbyPOIsData.value))
                    is Result.Error -> POIsState(ViewData.Error(nearbyPOIsData.error))
                }
            })
    }
}