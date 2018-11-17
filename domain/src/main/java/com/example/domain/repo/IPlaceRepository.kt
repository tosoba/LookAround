package com.example.domain.repo

import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.repo.model.ReverseGeocodingData
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface IPlaceRepository {
    fun getNearbyPlacesOfType(latLng: LatLng, type: String): Single<List<Place>>

    fun reverseGeocodeLocation(latLng: LatLng): Single<ReverseGeocodingData>

    fun getNearbyPOIs(latLng: LatLng): Single<NearbyPOIsData>

    fun getPlacesAutocompletePredictions(query: String): Single<List<Place>>
}