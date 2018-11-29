package com.example.data.repo.datastore

import com.example.data.api.GeocodingAPIClient
import com.example.data.preferences.AppPreferences
import com.example.data.util.ext.reverseGeocodingString
import com.example.data.util.ext.toBoundsWithRadius
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.repo.model.ReverseGeocodingData
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import io.ashdavies.rx.rxtasks.toSingle
import io.reactivex.Single
import javax.inject.Inject

class RemotePlacesDataStore @Inject constructor(
    private val geoDataClient: GeoDataClient,
    private val preferences: AppPreferences,
    private val geocodingAPIClient: GeocodingAPIClient
) : IRemotePlacesDataStore {

    override fun getNearbyPlacesOfType(
        latLng: LatLng,
        type: String
    ): Single<List<Place>> = Single.just(
        emptyList()
    )

    override fun reverseGeocodeLocation(
        latLng: LatLng
    ): Single<ReverseGeocodingData> = geocodingAPIClient.reverseGeocode(
        latLng = latLng.reverseGeocodingString
    ).map {
        if (it.isValid) ReverseGeocodingData.Success(it.formattedAddress)
        else ReverseGeocodingData.GeocodingError
    }

    override fun getNearbyPOIs(
        latLng: LatLng
    ): Single<NearbyPOIsData> = reverseGeocodeLocation(latLng).flatMap {
        when (it) {
            is ReverseGeocodingData.Success ->
                getPOIsNearbyAddress(latLng, it.address)
            is ReverseGeocodingData.GeocodingError ->
                Single.just(NearbyPOIsData.RemoteError.ReverseGeocodingError)
        }
    }

    override fun getPlacesAutocompletePredictions(
        query: String
    ): Single<List<Place>> = getPlacesFromAutocompletePrediction(query, null, null)

    private fun getPOIsNearbyAddress(
        latLng: LatLng,
        address: String
    ): Single<NearbyPOIsData> = getPlacesFromAutocompletePrediction(
        "$address points of interest",
        latLng.toBoundsWithRadius(preferences.radius.toDouble()),
        null
    ).map {
        if (it.isNotEmpty()) NearbyPOIsData.Success(it)
        else NearbyPOIsData.RemoteError.NoResultsError
    }

    private fun getPlacesFromAutocompletePrediction(
        query: String,
        bounds: LatLngBounds?,
        filter: AutocompleteFilter?
    ): Single<List<Place>> = geoDataClient.getAutocompletePredictions(query, bounds, filter).toSingle()
        .map {
            it.asIterable()
                .mapNotNull { prediction -> prediction.placeId }
                .chunked(20)
        }
        .toObservable()
        .flatMapIterable { it }
        .flatMap {
            geoDataClient.getPlaceById(*it.toTypedArray())
                .toSingle()
                .toObservable()
        }
        .map { it.toList() }
        .toList()
        .map { it.flatten() }
}