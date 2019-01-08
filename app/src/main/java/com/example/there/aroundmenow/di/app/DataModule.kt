package com.example.there.aroundmenow.di.app

import android.content.Context
import androidx.room.Room
import com.example.data.db.AppDb
import com.example.data.db.dao.FavouritePlacesDao
import com.example.data.repo.PlacesRepository
import com.example.data.repo.datastore.LocalPlacesDataStore
import com.example.data.repo.datastore.RemotePlacesDataStore
import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.datastore.ILocalPlacesDataStore
import com.example.domain.repo.datastore.IRemotePlacesDataStore
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Places
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
        @Singleton
        @JvmStatic
        fun appDb(context: Context): AppDb = Room.databaseBuilder(context, AppDb::class.java, "AppDb.db").build()

        @Provides
        @Singleton
        @JvmStatic
        fun favouritePlacesDao(appDb: AppDb): FavouritePlacesDao = appDb.favouritePlacesDao()
    }
}