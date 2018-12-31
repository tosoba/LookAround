package com.example.there.aroundmenow.di.app

import com.example.data.Keys
import com.example.data.repo.PlacesRepository
import com.example.data.repo.datastore.LocalPlacesDataStore
import com.example.data.repo.datastore.RemotePlacesDataStore
import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import se.walkercrou.places.GooglePlaces

@Module
abstract class DataModule {

    @Binds
    abstract fun localPlacesDataStore(ds: LocalPlacesDataStore): ILocalPlacesDataStore

    @Binds
    abstract fun remotePlacesDataStore(ds: RemotePlacesDataStore): IRemotePlacesDataStore

    @Binds
    abstract fun placesRepository(repository: PlacesRepository): IPlaceRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun googlePlaces(): GooglePlaces = GooglePlaces(Keys.GOOGLE_PLACES)
    }
}