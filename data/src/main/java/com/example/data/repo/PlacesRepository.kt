package com.example.data.repo

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.Result
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.FindNearbyPOIsResult
import com.example.domain.task.FindPlaceDetailsResult
import com.example.domain.task.ReverseGeocodeLocationResult
import com.example.domain.task.error.FindNearbyPOIsError
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val local: ILocalPlacesDataStore,
    private val remote: IRemotePlacesDataStore
) : IPlaceRepository {

    override fun reverseGeocodeLocation(
        latLng: LatLng
    ): Single<ReverseGeocodeLocationResult> = remote.reverseGeocodeLocation(latLng).map { result ->
        when (result) {
            is Result.Value -> result.mapToType()
            is Result.Error -> result.error.toRepositoryResult<ReverseGeocodeLocationResult> {
                result.mapTo(ReverseGeocodeLocationError.GeocodingError)
            }
        }
    }

    override fun findNearbyPOIs(
        latLng: LatLng
    ): Single<FindNearbyPOIsResult> = remote.findNearbyPOIs(latLng).map { result ->
        when (result) {
            is Result.Value -> result.mapToType()
            is Result.Error -> result.error.toRepositoryResult<FindNearbyPOIsResult> {
                result.mapTo(FindNearbyPOIsError.NoPOIsFound)
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
}