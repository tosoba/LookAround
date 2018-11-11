package com.example.domain.repo.model

import se.walkercrou.places.Place

sealed class NearbyPOIsData {
    class Success(val places: List<Place>) : NearbyPOIsData()

    class ReverseGeocodingError(val status: String) : NearbyPOIsData()
    object NoResultsError : NearbyPOIsData()
}