package com.example.domain.repo.datastore

import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.repo.model.SimplePOI
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface IRemotePlacesDataStore {
    fun reverseGeocodeLocation(latLng: LatLng): Single<DataStoreResult<GeocodingInfo>>
    fun findNearbyPOIs(latLng: LatLng): Single<DataStoreResult<List<SimplePOI>>>
}