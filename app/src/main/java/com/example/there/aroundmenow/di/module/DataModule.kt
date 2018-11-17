package com.example.there.aroundmenow.di.module

import android.content.Context
import com.example.data.repo.PlacesRepository
import com.example.data.repo.datastore.LocalPlacesDataStore
import com.example.data.repo.datastore.RemotePlacesDataStore
import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Places
import com.pacoworks.rxpaper2.RxPaperBook
import dagger.Binds
import dagger.Module
import dagger.Provides

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
        fun googlePlacesClient(context: Context): GeoDataClient = Places.getGeoDataClient(context)

        @Provides
        @JvmStatic
        fun placesDatabase(): RxPaperBook = RxPaperBook.with("places")
    }
}