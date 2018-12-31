package com.example.domain.repo.datastore

import com.example.domain.repo.Result
import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.repo.model.SimplePlace
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import se.walkercrou.places.Place

interface IRemotePlacesDataStore {
    fun reverseGeocodeLocation(latLng: LatLng): Single<Result<GeocodingInfo, DataStoreError>>

    fun findNearbyPOIs(latLng: LatLng): Single<Result<List<SimplePlace>, DataStoreError>>

    fun findNearbyPlacesOfType(
        latLng: LatLng,
        placeTypeQuery: String
    ): Single<Result<List<SimplePlace>, DataStoreError>>

    fun findPlaceDetails(simplePlace: SimplePlace): Single<Result<Place, DataStoreError>>
}