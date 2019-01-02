package com.example.there.aroundmenow.model

import android.os.Parcelable
import com.example.domain.repo.model.SimplePlace
import com.example.there.aroundmenow.util.ext.distanceTo
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

    companion object {
        fun fromDomainWithUserLatLng(
            domainPlace: SimplePlace,
            userLatLng: LatLng
        ): UISimplePlace = UISimplePlace(
            name = domainPlace.name,
            latLng = domainPlace.latLng,
            formattedDistanceFromUser = "${userLatLng.distanceTo(domainPlace.latLng)} meters away"
        )
    }
}