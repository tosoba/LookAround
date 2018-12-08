package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.Result
import com.example.domain.task.impl.FindNearbyPOIsTask
import com.example.there.aroundmenow.base.architecture.Data
import com.example.there.aroundmenow.base.architecture.RxActionsExecutor
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class POIsActionsExecutor @Inject constructor(
    poIsViewModel: POIsViewModel,
    private val findNearbyPOIsTask: FindNearbyPOIsTask
) : RxActionsExecutor<POIsState, POIsViewModel>(poIsViewModel), POIsActions {

    override fun findPOIsNearby(latLng: LatLng) {
        findNearbyPOIsTask.executeWithInput(latLng)
            .mapToStateThenSubscribeAndDisposeWithViewModel({ _, nearbyPOIsData ->
                when (nearbyPOIsData) {
                    is Result.Value -> POIsState(Data.Value(nearbyPOIsData.value))
                    is Result.Error -> POIsState(Data.Error(nearbyPOIsData.error))
                }
            })
    }
}