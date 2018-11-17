package com.example.domain.repo.model

import com.google.android.gms.location.places.Place

sealed class NearbyPOIsData {
    class Success(val places: List<Place>) : NearbyPOIsData()

    sealed class RemoteError : NearbyPOIsData() {
        class ReverseGeocodingError(val status: String) : RemoteError()
        object NoResultsError : RemoteError()
    }

    sealed class LocalError : NearbyPOIsData() {
        object NoResultsError : LocalError()
        object SavedPOIsNotCloseEnoughError : LocalError()
    }
}