package com.example.domain.repo.datastore

import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.repo.model.ReverseGeocodingData
import io.reactivex.Single
import se.walkercrou.places.Place

interface IRemotePlacesDataStore {
    fun getNearbyPlacesOfType(
        latitude: Double,
        longitude: Double,
        type: String
    ): Single<List<Place>>

    fun reverseGeocodeLocation(
        latitude: Double,
        longitude: Double
    ): Single<ReverseGeocodingData>

    fun getNearbyPOIs(
        latitude: Double,
        longitude: Double
    ): Single<NearbyPOIsData>
}