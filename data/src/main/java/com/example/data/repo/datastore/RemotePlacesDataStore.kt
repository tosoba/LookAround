package com.example.data.repo.datastore

import com.example.data.api.geocoding.GeocodingAPIClient
import com.example.data.api.overpass.OverpassAPIClient
import com.example.data.preferences.AppPreferences
import com.example.data.util.ext.reverseGeocodingString
import com.example.data.util.ext.toOverpassPOIsQueryWithRadius
import com.example.domain.repo.datastore.DataStoreResult
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.repo.model.SimplePOI
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class RemotePlacesDataStore @Inject constructor(
    private val geoDataClient: GeoDataClient,
    private val preferences: AppPreferences,
    private val geocodingAPIClient: GeocodingAPIClient,
    private val overpassAPIClient: OverpassAPIClient
) : IRemotePlacesDataStore {

    override fun reverseGeocodeLocation(
        latLng: LatLng
    ): Single<DataStoreResult<GeocodingInfo>> = geocodingAPIClient.reverseGeocode(
        latLng = latLng.reverseGeocodingString
    ).map {
        if (it.isValid) DataStoreResult.Value(GeocodingInfo(latLng, it.formattedAddress))
        else DataStoreResult.Invalid
    }

    override fun findNearbyPOIs(
        latLng: LatLng
    ): Single<DataStoreResult<List<SimplePOI>>> = overpassAPIClient.getPlaces(
        latLng.toOverpassPOIsQueryWithRadius(
            radius = preferences.radius
        )
    ).flatMap { response ->
        if (response.places.isEmpty())
            return@flatMap Single.just(DataStoreResult.Empty)
        else Single.just(response.places.filter { it.tags.name != null }).flatMap { places ->
            if (places.isEmpty()) Single.just(DataStoreResult.Invalid)
            else Single.just(DataStoreResult.Value(places.map {
                SimplePOI(LatLng(it.latitude, it.longitude), it.tags.name!!)
            }))
        }
    }
}