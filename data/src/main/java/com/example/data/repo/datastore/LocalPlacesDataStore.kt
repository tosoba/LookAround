package com.example.data.repo.datastore

import android.location.Location
import com.example.data.db.DataKeys
import com.example.data.db.model.SavedPOIsLocation
import com.example.data.util.ext.isWithinDistanceFrom
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.model.NearbyPOIsData
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import se.walkercrou.places.Place
import javax.inject.Inject

class LocalPlacesDataStore @Inject constructor(
    private val db: RxPaperBook
) : ILocalPlacesDataStore {

    override fun saveNearbyPOIs(
        latitude: Double,
        longitude: Double,
        pois: List<Place>
    ): Completable = Completable.merge(
        listOf(
            db.write(DataKeys.location, SavedPOIsLocation(latitude, longitude)),
            db.write(DataKeys.pois, pois)
        )
    )

    private fun areSavedPOIsCloseEnough(
        latitude: Double,
        longitude: Double,
        limitInMeters: Int = 10000
    ): Single<Boolean> = db.read<SavedPOIsLocation>(DataKeys.location).map { saved ->
        val currentLocation = Location("").also {
            it.latitude = latitude
            it.longitude = longitude
        }
        saved.location.isWithinDistanceFrom(currentLocation, distance = limitInMeters)
    }

    private val anyPOIsSaved: Single<Boolean>
        get() = Single.zip(
            db.contains(DataKeys.pois),
            db.contains(DataKeys.location),
            BiFunction { containsPOIs, containsLocation ->
                containsPOIs && containsLocation
            }
        )

    override fun getLastNearbyPOIs(
        latitude: Double,
        longitude: Double
    ): Single<NearbyPOIsData> = anyPOIsSaved.flatMap { poisAreSaved ->
        if (poisAreSaved) areSavedPOIsCloseEnough(latitude, longitude).flatMap { closeEnough ->
            if (closeEnough) db.read<List<Place>>(DataKeys.pois).map { NearbyPOIsData.Success(it) }
            else Single.just(NearbyPOIsData.LocalError.SavedPOIsNotCloseEnoughError)
        }
        else Single.just(NearbyPOIsData.LocalError.NoResultsError)
    }
}