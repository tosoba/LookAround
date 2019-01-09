package com.example.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.repo.model.SavedPlace
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "favourite_places")
data class FavouritePlaceData(
    @PrimaryKey val id: String,
    val name: String,
    val lat: Double,
    val lng: Double,
    val address: String?,
    val phoneNumber: String?,
    val websiteUri: String?,
    val rating: Float,
    val placeTypes: List<Int>
) {
    val domain: SavedPlace
        get() = SavedPlace(id, name, LatLng(lat, lng), address, phoneNumber, websiteUri, rating, placeTypes)

    companion object {
        fun fromDomain(savedPlace: SavedPlace): FavouritePlaceData = FavouritePlaceData(
            id = savedPlace.id,
            name = savedPlace.name,
            lat = savedPlace.latLng.latitude,
            lng = savedPlace.latLng.longitude,
            address = savedPlace.address,
            phoneNumber = savedPlace.phoneNumber,
            websiteUri = savedPlace.websiteUri,
            rating = savedPlace.rating,
            placeTypes = savedPlace.placeTypes
        )
    }
}