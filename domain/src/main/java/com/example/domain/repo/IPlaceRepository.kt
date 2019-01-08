package com.example.domain.repo

import com.example.domain.repo.model.SavedPlace
import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.*
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IPlaceRepository {
    fun reverseGeocodeLocation(latLng: LatLng): Single<ReverseGeocodeLocationResult>

    fun findNearbyPOIs(latLng: LatLng): Single<FindNearbyPlacesResult>

    fun findNearbyPlacesOfType(
        latLng: LatLng,
        placeTypeQuery: String
    ): Single<FindNearbyPlacesResult>

    fun findPlaceDetails(simplePlace: SimplePlace): Single<FindPlaceDetailsResult>

    fun findPlacePhotos(id: String): Single<FindPlacePhotosResult>

    fun addPlaceToFavourites(savedPlace: SavedPlace): Completable

    fun getFavouritePlaces(): Flowable<GetFavouritePlacesResult>
}