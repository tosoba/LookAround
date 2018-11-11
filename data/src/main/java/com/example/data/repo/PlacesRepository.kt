package com.example.data.repo

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.repo.model.ReverseGeocodingData
import io.reactivex.Single
import se.walkercrou.places.Place
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val local: ILocalPlacesDataStore,
    private val remote: IRemotePlacesDataStore
) : IPlaceRepository {

    override fun getNearbyPlacesOfType(
        latitude: Double,
        longitude: Double,
        type: String
    ): Single<List<Place>> = remote.getNearbyPlacesOfType(latitude, longitude, type)

    override fun reverseGeocodeLocation(
        latitude: Double,
        longitude: Double
    ): Single<ReverseGeocodingData> = remote.reverseGeocodeLocation(latitude, longitude)

    override fun getNearbyPOIs(
        latitude: Double,
        longitude: Double
    ): Single<NearbyPOIsData> = remote.getNearbyPOIs(latitude, longitude)
}