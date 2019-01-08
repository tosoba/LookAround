package com.example.domain.repo.datastore

import com.example.domain.repo.model.SavedPlace
import io.reactivex.Completable
import io.reactivex.Flowable

interface ILocalPlacesDataStore {
    fun addPlaceToFavourites(place: SavedPlace): Completable
    fun getFavouritePlaces(): Flowable<List<SavedPlace>>
}