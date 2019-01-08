package com.example.data.repo.datastore

import com.example.data.db.dao.FavouritePlacesDao
import com.example.data.db.model.FavouritePlaceData
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.model.SavedPlace
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class LocalPlacesDataStore @Inject constructor(
    private val favouritePlacesDao: FavouritePlacesDao
) : ILocalPlacesDataStore {

    override fun addPlaceToFavourites(place: SavedPlace): Completable = favouritePlacesDao.insertPlaces(
        FavouritePlaceData.fromDomain(place)
    )

    override fun getFavouritePlaces(): Flowable<List<SavedPlace>> = favouritePlacesDao.getPlaces().map { savedPlaces ->
        savedPlaces.map { it.domain }
    }
}