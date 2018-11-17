package com.example.data.repo.datastore

import com.example.data.db.DataKeys
import com.example.data.db.model.SavedPOIsLocation
import com.example.data.util.ext.isWithinDistanceFrom
import com.example.data.util.ext.location
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.model.NearbyPOIsData
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class LocalPlacesDataStore @Inject constructor(
    private val db: RxPaperBook
) : ILocalPlacesDataStore {

    override fun saveNearbyPOIs(
        latLng: LatLng,
        pois: List<Place>
    ): Completable = Completable.merge(
        listOf(
            db.write(DataKeys.location, SavedPOIsLocation(latLng)),
            db.write(DataKeys.pois, pois)
        )
    )

    private fun areSavedPOIsCloseEnough(
        latLng: LatLng,
        limitInMeters: Int = 10000
    ): Single<Boolean> = db.read<SavedPOIsLocation>(DataKeys.location).map {
        it.location.isWithinDistanceFrom(latLng.location, distance = limitInMeters)
    }

    private val anyPOIsSaved: Single<Boolean>
        get() = Single.zip(
            db.contains(DataKeys.pois),
            db.contains(DataKeys.location),
            BiFunction { containsPOIs, containsLocation -> containsPOIs && containsLocation }
        )

    override fun getLastNearbyPOIs(
        latLng: LatLng
    ): Single<NearbyPOIsData> = anyPOIsSaved.flatMap { poisAreSaved ->
        if (poisAreSaved) areSavedPOIsCloseEnough(latLng).flatMap { closeEnough ->
            if (closeEnough) db.read<List<Place>>(DataKeys.pois).map { NearbyPOIsData.Success(it) }
            else Single.just(NearbyPOIsData.LocalError.SavedPOIsNotCloseEnoughError)
        }
        else Single.just(NearbyPOIsData.LocalError.NoResultsError)
    }
}