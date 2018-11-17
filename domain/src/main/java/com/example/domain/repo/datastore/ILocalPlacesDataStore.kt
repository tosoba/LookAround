package com.example.domain.repo.datastore

import com.example.domain.repo.model.NearbyPOIsData
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalPlacesDataStore {
    fun saveNearbyPOIs(latLng: LatLng, pois: List<Place>): Completable
    fun getLastNearbyPOIs(latLng: LatLng): Single<NearbyPOIsData>
}