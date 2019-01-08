package com.example.there.aroundmenow.model

import android.os.Parcelable
import com.example.data.util.ext.formattedDistanceTo
import com.example.domain.repo.model.SavedPlace
import com.example.domain.repo.model.SimplePlace
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UISimplePlace(
    val name: String,
    val latLng: LatLng,
    val formattedDistanceFromUser: String
) : Parcelable {

    val domain: SimplePlace
        get() = SimplePlace(latLng, name)

    val markerOptions: MarkerOptions
        get() = MarkerOptions().position(latLng).title(name).snippet(formattedDistanceFromUser)

    val extendedMarkerOptions: com.androidmapsextensions.MarkerOptions
        get() = com.androidmapsextensions.MarkerOptions()
            .position(latLng)
            .title(name)
            .snippet(formattedDistanceFromUser)

    companion object {
        fun fromDomainWithUserLatLng(
            domainPlace: SimplePlace,
            userLatLng: LatLng
        ): UISimplePlace = UISimplePlace(
            name = domainPlace.name,
            latLng = domainPlace.latLng,
            formattedDistanceFromUser = domainPlace.latLng.formattedDistanceTo(userLatLng)
        )

        fun fromGooglePlaceWithUserLatLng(
            place: Place,
            userLatLng: LatLng
        ): UISimplePlace = UISimplePlace(
            name = place.name.toString(),
            latLng = place.latLng,
            formattedDistanceFromUser = place.latLng.formattedDistanceTo(userLatLng)
        )

        fun fromSavedPlace(savedPlace: SavedPlace): UISimplePlace = UISimplePlace(
            name = savedPlace.name,
            latLng = savedPlace.latLng,
            formattedDistanceFromUser = ""
        )

        fun fromSavedPlaceWithUserLatLng(
            savedPlace: SavedPlace,
            userLatLng: LatLng
        ): UISimplePlace = UISimplePlace(
            name = savedPlace.name,
            latLng = savedPlace.latLng,
            formattedDistanceFromUser = savedPlace.latLng.formattedDistanceTo(userLatLng)
        )
    }
}