package com.example.domain.repo.datastore

import android.graphics.Bitmap
import com.example.domain.repo.Result
import com.example.domain.repo.model.SimplePlace
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface IRemotePlacesDataStore {

    fun findNearbyPOIs(latLng: LatLng): Single<Result<List<SimplePlace>, DataStoreError>>

    fun findNearbyPlacesOfType(
        latLng: LatLng,
        placeTypeQuery: String
    ): Single<Result<List<SimplePlace>, DataStoreError>>

    fun findPlaceDetails(simplePlace: SimplePlace): Single<Result<Place, DataStoreError>>

    fun findPlacePhotos(id: String): Single<Result<List<Bitmap>, DataStoreError>>
}