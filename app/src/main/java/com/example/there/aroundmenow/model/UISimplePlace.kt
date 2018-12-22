package com.example.there.aroundmenow.model

import android.os.Parcelable
import com.example.domain.repo.model.SimplePlace
import com.example.there.aroundmenow.util.ext.distanceTo
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UISimplePlace(
    val name: String,
    val latLng: LatLng,
    val formattedDistanceFromUser: String
) : Parcelable {

    val domain: SimplePlace
        get() = SimplePlace(latLng, name)

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