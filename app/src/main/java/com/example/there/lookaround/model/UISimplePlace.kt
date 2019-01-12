package com.example.there.lookaround.model

import android.os.Parcelable
import com.example.data.util.ext.formattedDistanceTo
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

        fun fromGooglePlace(
            place: Place
        ): UISimplePlace = UISimplePlace(
            name = place.name.toString(),
            latLng = place.latLng,
            formattedDistanceFromUser = ""
        )

        fun fromGooglePlaceWithUserLatLng(
            place: Place,
            userLatLng: LatLng
        ): UISimplePlace = UISimplePlace(
            name = place.name.toString(),
            latLng = place.latLng,
            formattedDistanceFromUser = place.latLng.formattedDistanceTo(userLatLng)
        )

        fun fromUIPlace(place: UIPlace): UISimplePlace = UISimplePlace(
            name = place.name,
            latLng = place.latLng,
            formattedDistanceFromUser = ""
        )

        fun fromUIPlaceWithUserLatLng(
            place: UIPlace,
            userLatLng: LatLng
        ): UISimplePlace = UISimplePlace(
            name = place.name,
            latLng = place.latLng,
            formattedDistanceFromUser = place.latLng.formattedDistanceTo(userLatLng)
        )
    }
}