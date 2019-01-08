package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.FavouritePlacesDao
import com.example.data.db.model.FavouritePlaceData

@Database(entities = [FavouritePlaceData::class], version = 1)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class AppDb : RoomDatabase() {
    abstract fun favouritePlacesDao(): FavouritePlacesDao
}