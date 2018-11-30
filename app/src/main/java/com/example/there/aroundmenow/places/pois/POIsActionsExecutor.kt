package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.RepositoryResult
import com.example.domain.task.impl.FindNearbyPOIs
import com.example.domain.task.result.FindNearbyPOIsResult
import com.example.there.aroundmenow.base.architecture.RxActionsExecutor
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class POIsActionsExecutor @Inject constructor(
    poIsViewModel: POIsViewModel,
    private val findNearbyPOIs: FindNearbyPOIs
) : RxActionsExecutor<POIsState, POIsViewModel>(poIsViewModel), POIsActions {

    override fun findPOIsNearby(latLng: LatLng) {
        findNearbyPOIs.executeWithEventArgs(latLng, { it })
            .mapToStateThenSubscribeAndDisposeWithViewModel({ currentState, nearbyPOIsData ->
                when (nearbyPOIsData) {
                    is FindNearbyPOIsResult.Data -> {
                        val result = nearbyPOIsData.result
                        when (result) {
                            is RepositoryResult.Value -> POIsState(result.value)
                            is RepositoryResult.Error -> currentState
                        }
                    }
                    FindNearbyPOIsResult.NoPOIsFound -> currentState
                }
            })
    }
}