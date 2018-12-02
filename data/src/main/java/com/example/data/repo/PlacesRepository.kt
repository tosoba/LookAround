package com.example.data.repo

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.Result
import com.example.domain.repo.datastore.DataStoreError
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
    ): Single<ReverseGeocodeLocationResult> = remote.reverseGeocodeLocation(latLng).map {
        when (it) {
            is Result.Value -> it.withErrorType()
            is Result.Error -> {
                val error = it.error
                when (error) {
                    is DataStoreError.Data -> it.withError<ReverseGeocodeLocationError>(
                        ReverseGeocodeLocationError.GeocodingError
                    )
                    is DataStoreError.Exception -> it.withError<ReverseGeocodeLocationError>(
                        ReverseGeocodeLocationError.Exception(error.throwable)
                    )
                }
            }
        }
    }.onErrorReturn { Result.Error(ReverseGeocodeLocationError.Exception(it)) }

    override fun findNearbyPOIs(
        latLng: LatLng
    ): Single<FindNearbyPOIsResult> = remote.findNearbyPOIs(latLng).map {
        when (it) {
            is Result.Value -> it.withErrorType()
            is Result.Error -> {
                val error = it.error
                when (error) {
                    is DataStoreError.Data -> it.withError<FindNearbyPOIsError>(
                        FindNearbyPOIsError.NoPOIsFound
                    )
                    is DataStoreError.Exception -> it.withError<FindNearbyPOIsError>(
                        FindNearbyPOIsError.Exception(error.throwable)
                    )
                }
            }
        }
    }.onErrorReturn { Result.Error(FindNearbyPOIsError.Exception(it)) }
}