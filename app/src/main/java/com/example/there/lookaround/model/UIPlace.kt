package com.example.there.lookaround.model

import android.os.Parcelable
import com.example.domain.repo.model.SavedPlace
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UIPlace(
    val id: String,
    val name: String,
    val latLng: LatLng,
    val address: String?,
    val phoneNumber: String?,
    val websiteUri: String?,
    val rating: Float,
    val placeTypes: List<Int>
) : Parcelable {
    val domain: SavedPlace
        get() = SavedPlace(
            id,
            name,
            latLng,
            address,
            phoneNumber,
            websiteUri,
            rating,
            placeTypes
        )

    companion object {
        fun fromDomain(savedPlace: SavedPlace): UIPlace = UIPlace(
            savedPlace.id,
            savedPlace.name,
            savedPlace.latLng,
            savedPlace.address,
            savedPlace.phoneNumber,
            savedPlace.websiteUri,
            savedPlace.rating,
            savedPlace.placeTypes
        )
    }
}