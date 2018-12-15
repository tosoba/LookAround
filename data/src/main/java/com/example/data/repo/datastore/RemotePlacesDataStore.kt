package com.example.data.repo.datastore

import com.example.data.api.geocoding.GeocodingAPIClient
import com.example.data.api.overpass.OverpassAPIClient
import com.example.data.preferences.AppPreferences
import com.example.data.util.ext.reverseGeocodingString
import com.example.data.util.ext.toOverpassPOIsQueryWithRadius
import com.example.domain.repo.Result
import com.example.domain.repo.datastore.DataStoreError
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
    ): Single<Result<GeocodingInfo, DataStoreError>> = geocodingAPIClient.reverseGeocode(
        latLng = latLng.reverseGeocodingString
    ).map {
        if (it.isValid) Result.Value<GeocodingInfo, DataStoreError>(GeocodingInfo(latLng, it.formattedAddress))
        else Result.Error<GeocodingInfo, DataStoreError>(DataStoreError.Invalid)
    }

    override fun findNearbyPOIs(
        latLng: LatLng
    ): Single<Result<List<SimplePOI>, DataStoreError>> = overpassAPIClient.getPlaces(
        latLng.toOverpassPOIsQueryWithRadius(preferences.radius)
    ).flatMap { response ->
        if (response.places.isEmpty())
            return@flatMap Single.just(Result.Error<List<SimplePOI>, DataStoreError>(DataStoreError.Empty))
        else Single.just(response.places.filter { it.tags.name != null }).flatMap { places ->
            if (places.isEmpty()) Single.just(Result.Error<List<SimplePOI>, DataStoreError>(DataStoreError.Invalid))
            else Single.just(Result.Value<List<SimplePOI>, DataStoreError>(places.map {
                SimplePOI(LatLng(it.latitude, it.longitude), it.tags.name!!)
            }))
        }
    }
}