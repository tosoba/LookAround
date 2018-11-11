package com.example.domain.repo.datastore

import com.example.domain.repo.model.NearbyPOIsData
import io.reactivex.Completable
import io.reactivex.Single
import se.walkercrou.places.Place

interface ILocalPlacesDataStore {
    fun saveNearbyPOIs(latitude: Double, longitude: Double, pois: List<Place>): Completable
    fun getLastNearbyPOIs(latitude: Double, longitude: Double): Single<NearbyPOIsData>
}