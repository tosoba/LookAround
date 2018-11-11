package com.example.data.repo.datastore

import com.example.data.AppPreferences
import com.example.data.api.GeocodingAPIClient
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.repo.model.ReverseGeocodingData
import io.reactivex.Single
import se.walkercrou.places.GooglePlaces
import se.walkercrou.places.Place
import se.walkercrou.places.TypeParam
import javax.inject.Inject

class RemotePlacesDataStore @Inject constructor(
    private val googlePlaces: GooglePlaces,
    private val preferences: AppPreferences,
    private val geocodingAPIClient: GeocodingAPIClient
) : IRemotePlacesDataStore {

    override fun getNearbyPlacesOfType(
        latitude: Double,
        longitude: Double,
        type: String
    ): Single<List<Place>> = Single.just(
        googlePlaces.getNearbyPlaces(
            latitude,
            longitude,
            preferences.radius.toDouble(),
            TypeParam.name("types").value(type)
        )
    )

    override fun reverseGeocodeLocation(
        latitude: Double,
        longitude: Double
    ): Single<ReverseGeocodingData> = geocodingAPIClient.reverseGeocode(
        latitude = latitude,
        longitude = longitude
    ).map {
        if (it.hasResults) ReverseGeocodingData.Success(it.results.first().formattedAddress)
        else ReverseGeocodingData.GeocodingError(it.status)
    }

    override fun getNearbyPOIs(
        latitude: Double,
        longitude: Double
    ): Single<NearbyPOIsData> = reverseGeocodeLocation(latitude, longitude).flatMap {
        when (it) {
            is ReverseGeocodingData.Success -> getPOIsNearbyAddress(it.address)
            is ReverseGeocodingData.GeocodingError -> Single.just(NearbyPOIsData.ReverseGeocodingError(it.status))
        }
    }

    private fun getPOIsNearbyAddress(
        address: String
    ): Single<NearbyPOIsData> = Single.just(googlePlaces.getPlacesByQuery("$address points of interest")).map {
        if (it.isNotEmpty()) NearbyPOIsData.Success(it)
        else NearbyPOIsData.NoResultsError
    }
}