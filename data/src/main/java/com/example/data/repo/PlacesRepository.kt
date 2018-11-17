package com.example.data.repo

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.repo.model.ReverseGeocodingData
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val local: ILocalPlacesDataStore,
    private val remote: IRemotePlacesDataStore
) : IPlaceRepository {

    override fun getNearbyPlacesOfType(
        latLng: LatLng,
        type: String
    ): Single<List<Place>> = remote.getNearbyPlacesOfType(latLng, type)

    override fun reverseGeocodeLocation(
        latLng: LatLng
    ): Single<ReverseGeocodingData> = remote.reverseGeocodeLocation(latLng)

    override fun getNearbyPOIs(
        latLng: LatLng
    ): Single<NearbyPOIsData> = local.getLastNearbyPOIs(latLng).flatMap { localPOIs ->
        when (localPOIs) {
            is NearbyPOIsData.Success -> Single.just(localPOIs)
            else -> remote.getNearbyPOIs(latLng).flatMap { remotePOIs ->
                when (remotePOIs) {
                    is NearbyPOIsData.Success -> Single.just(remotePOIs).flatMap { poisToSave ->
                        local.saveNearbyPOIs(latLng, poisToSave.places)
                            .andThen(Single.just(poisToSave))
                    }
                    else -> Single.just(remotePOIs)
                }
            }
        }
    }

    override fun getPlacesAutocompletePredictions(
        query: String
    ): Single<List<Place>> = remote.getPlacesAutocompletePredictions(query)
}