package com.example.data.repo

import com.example.data.util.ext.location
import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.Result
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.repo.model.ReverseGeocodingInfo
import com.example.domain.repo.model.SavedPlace
import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.*
import com.example.domain.task.error.*
import com.google.android.gms.maps.model.LatLng
import com.patloew.rxlocation.RxLocation
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val local: ILocalPlacesDataStore,
    private val remote: IRemotePlacesDataStore,
    private val rxLocation: RxLocation
) : IPlaceRepository {

    override fun reverseGeocodeLocation(
        latLng: LatLng
    ): Single<ReverseGeocodeLocationResult> = rxLocation.geocoding()
        .fromLocation(latLng.location)
        .map<ReverseGeocodeLocationResult> { Result.Value(ReverseGeocodingInfo(it)) }
        .switchIfEmpty(
            Single.just(
                Result.Error<ReverseGeocodingInfo, ReverseGeocodeLocationError>(
                    ReverseGeocodeLocationError.GeocodingFailed
                )
            )
        )

    override fun findNearbyPOIs(
        latLng: LatLng
    ): Single<FindNearbyPlacesResult> = remote.findNearbyPOIs(latLng).map { result ->
        when (result) {
            is Result.Value -> result.mapToType()
            is Result.Error -> result.error.toRepositoryResult<FindNearbyPlacesResult> {
                result.mapTo(FindNearbyPlacesError.NoPlacesFound)
            }
        }
    }

    override fun findNearbyPlacesOfType(
        latLng: LatLng,
        placeTypeQuery: String
    ): Single<FindNearbyPlacesResult> = remote.findNearbyPlacesOfType(latLng, placeTypeQuery).map { result ->
        when (result) {
            is Result.Value -> result.mapToType()
            is Result.Error -> result.error.toRepositoryResult<FindNearbyPlacesResult> {
                result.mapTo(FindNearbyPlacesError.NoPlacesFound)
            }
        }
    }

    override fun findPlaceDetails(
        simplePlace: SimplePlace
    ): Single<FindPlaceDetailsResult> = remote.findPlaceDetails(simplePlace).map {
        when (it) {
            is Result.Value -> it.mapToType()
            is Result.Error -> it.error.toRepositoryResult<FindPlaceDetailsResult> {
                it.mapTo(FindPlaceDetailsError.PlaceDetailsNotFound)
            }
        }
    }

    override fun findPlacePhotos(
        id: String
    ): Single<FindPlacePhotosResult> = remote.findPlacePhotos(id).map {
        when (it) {
            is Result.Value -> it.mapToType()
            is Result.Error -> it.error.toRepositoryResult<FindPlacePhotosResult> {
                it.mapTo(FindPlacePhotosError.NoPhotosFound)
            }
        }
    }

    override fun addPlaceToFavourites(savedPlace: SavedPlace): Completable = local.addPlaceToFavourites(savedPlace)

    override fun getFavouritePlaces(): Flowable<GetFavouritePlacesResult> = local.getFavouritePlaces().map {
        Result.Value<List<SavedPlace>, GetFavouritePlacesError>(it)
    }
}