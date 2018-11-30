package com.example.data.repo

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.RepositoryResult
import com.example.domain.repo.datastore.DataStoreResult
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.task.result.FindNearbyPOIsResult
import com.example.domain.task.result.ReverseGeocodeLocationResult
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
            is DataStoreResult.Value -> ReverseGeocodeLocationResult.Data(RepositoryResult.Value(it.value))
            is DataStoreResult.Empty, DataStoreResult.Invalid -> ReverseGeocodeLocationResult.GeocodingError
            is DataStoreResult.Error -> ReverseGeocodeLocationResult.Data(RepositoryResult.Error(it.throwable))
        }
    }.onErrorReturn { ReverseGeocodeLocationResult.Data(RepositoryResult.Error(it)) }

    override fun findNearbyPOIs(
        latLng: LatLng
    ): Single<FindNearbyPOIsResult> = remote.findNearbyPOIs(latLng).map {
        when (it) {
            is DataStoreResult.Value -> FindNearbyPOIsResult.Data(RepositoryResult.Value(it.value))
            is DataStoreResult.Empty, DataStoreResult.Invalid -> FindNearbyPOIsResult.NoPOIsFound
            is DataStoreResult.Error -> FindNearbyPOIsResult.Data(RepositoryResult.Error(it.throwable))
        }
    }.onErrorReturn { FindNearbyPOIsResult.Data(RepositoryResult.Error(it)) }
}