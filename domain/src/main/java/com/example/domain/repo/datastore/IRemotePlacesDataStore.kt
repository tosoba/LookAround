package com.example.domain.repo.datastore

import com.example.domain.repo.Result
import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.repo.model.SimplePlace
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface IRemotePlacesDataStore {
    fun reverseGeocodeLocation(latLng: LatLng): Single<Result<GeocodingInfo, DataStoreError>>
    fun findNearbyPOIs(latLng: LatLng): Single<Result<List<SimplePlace>, DataStoreError>>
    fun findPlaceDetails(simplePlace: SimplePlace): Single<Result<Place, DataStoreError>>
}