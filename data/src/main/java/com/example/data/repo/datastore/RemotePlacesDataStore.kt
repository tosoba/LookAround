package com.example.data.repo.datastore

import android.graphics.Bitmap
import com.example.data.api.overpass.OverpassAPIClient
import com.example.data.api.overpass.model.OverpassPlacesResponse
import com.example.data.preferences.AppPreferences
import com.example.data.util.ext.toBoundsWithRadius
import com.example.data.util.ext.toOverpassPOIsQueryWithRadius
import com.example.data.util.ext.toOverpassQueryWithRadius
import com.example.domain.repo.Result
import com.example.domain.repo.datastore.DataStoreError
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.example.domain.repo.model.SimplePlace
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.ashdavies.rx.rxtasks.toSingle
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RemotePlacesDataStore @Inject constructor(
    private val geoDataClient: GeoDataClient,
    private val preferences: AppPreferences,
    private val overpassAPIClient: OverpassAPIClient
) : IRemotePlacesDataStore {

    override fun findNearbyPOIs(
        latLng: LatLng
    ): Single<Result<List<SimplePlace>, DataStoreError>> = overpassAPIClient.getPlaces(
        query = latLng.toOverpassPOIsQueryWithRadius(preferences.radius)
    ).result

    override fun findNearbyPlacesOfType(
        latLng: LatLng,
        placeTypeQuery: String
    ): Single<Result<List<SimplePlace>, DataStoreError>> = overpassAPIClient.getPlaces(
        query = latLng.toOverpassQueryWithRadius(
            query = placeTypeQuery,
            radius = preferences.radius
        )
    ).result

    private val Single<OverpassPlacesResponse>.result: Single<Result<List<SimplePlace>, DataStoreError>>
        get() = flatMap { response ->
            if (response.places.isEmpty())
                return@flatMap Single.just(Result.Error<List<SimplePlace>, DataStoreError>(DataStoreError.Empty))
            else Single.just(response.places.filter { it.tags.name != null }).flatMap { places ->
                if (places.isEmpty()) Single.just(Result.Error<List<SimplePlace>, DataStoreError>(DataStoreError.Invalid))
                else Single.just(Result.Value<List<SimplePlace>, DataStoreError>(places.map {
                    SimplePlace(LatLng(it.latitude, it.longitude), it.tags.name!!)
                }))
            }
        }

    override fun findPlaceDetails(
        simplePlace: SimplePlace
    ): Single<Result<Place, DataStoreError>> = geoDataClient.getAutocompletePredictions(
        simplePlace.name,
        simplePlace.latLng.toBoundsWithRadius(2000.0),
        null
    ).toSingle().flatMap { response ->
        val results = response.toList()
        if (results.isEmpty()) Single.just(Result.Error<Place, DataStoreError>(DataStoreError.Empty))
        else geoDataClient.getPlaceById(results[0].placeId).toSingle().map {
            if (it.count == 0) Result.Error<Place, DataStoreError>(DataStoreError.Empty)
            else Result.Value<Place, DataStoreError>(it.get(0))
        }
    }

    override fun findPlacePhotos(
        id: String
    ): Single<Result<List<Bitmap>, DataStoreError>> = geoDataClient.getPlacePhotos(id).toSingle().flatMap { response ->
        if (response.photoMetadata.count == 0)
            Single.just(Result.Error<List<Bitmap>, DataStoreError>(DataStoreError.Empty))
        else Observable.fromIterable(response.photoMetadata.take(5))
            .flatMap { geoDataClient.getPhoto(it).toSingle().toObservable() }
            .toList()
            .map { photoResponses -> Result.Value<List<Bitmap>, DataStoreError>(photoResponses.map { it.bitmap }) }
    }
}