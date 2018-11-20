package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.task.impl.FindNearbyPOIs
import com.example.there.aroundmenow.base.architecture.RxPresenter
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class POIsPresenter @Inject constructor(
    private val findNearbyPOIs: FindNearbyPOIs
) : RxPresenter<POIsState, POIsViewModel>() {

    fun findPOIsNearby(latLng: LatLng) {
        findNearbyPOIs.executeWithEventArgs(latLng, { it })
            .mapToStateThenSubscribeAndDisposeWithViewModel({ currentState, nearbyPOIsData ->
                when (nearbyPOIsData) {
                    is NearbyPOIsData.Success -> POIsState(nearbyPOIsData.places)
                    is NearbyPOIsData.RemoteError.ReverseGeocodingError -> currentState
                    is NearbyPOIsData.RemoteError.NoResultsError, NearbyPOIsData.LocalError.NoResultsError -> currentState
                    is NearbyPOIsData.LocalError.SavedPOIsNotCloseEnoughError -> currentState
                }
            })
    }
}