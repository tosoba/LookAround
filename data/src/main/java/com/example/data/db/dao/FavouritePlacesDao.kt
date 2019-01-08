package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.model.FavouritePlaceData
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface FavouritePlacesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaces(vararg places: FavouritePlaceData): Completable

    @Query(value = "SELECT * FROM favourite_places")
    fun getPlaces(): Flowable<List<FavouritePlaceData>>
}