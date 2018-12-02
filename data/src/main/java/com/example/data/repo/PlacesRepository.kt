package com.example.data.repo

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.Result
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.task.FindNearbyPOIsResult
import com.example.domain.task.ReverseGeocodeLocationResult
import com.example.domain.task.error.FindNearbyPOIsError
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
            is Result.Value -> result.toErrorType()
            is Result.Error -> result.error.toResult<ReverseGeocodeLocationResult>(
                onException = { result.withError(ReverseGeocodeLocationError.Exception(it)) },
                onDataError = { result.withError(ReverseGeocodeLocationError.GeocodingError) }
            )
        }

    }.onErrorReturn { Result.Error(ReverseGeocodeLocationError.Exception(it)) }

    override fun findNearbyPOIs(
        latLng: LatLng
    ): Single<FindNearbyPOIsResult> = remote.findNearbyPOIs(latLng).map { result ->
        when (result) {
            is Result.Value -> result.toErrorType()
            is Result.Error -> result.error.toResult<FindNearbyPOIsResult>(
                onException = { result.withError(FindNearbyPOIsError.Exception(it)) },
                onDataError = { result.withError(FindNearbyPOIsError.NoPOIsFound) }
            )
        }
    }.onErrorReturn { Result.Error(FindNearbyPOIsError.Exception(it)) }
}