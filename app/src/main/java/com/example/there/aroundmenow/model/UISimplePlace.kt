package com.example.there.aroundmenow.model

import android.os.Parcelable
import com.example.domain.repo.model.SimplePlace
import com.example.there.aroundmenow.util.ext.distanceTo
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UISimplePlace(
    val name: String,
    val formattedDistanceFromUser: String
) : Parcelable {
    companion object {
        fun fromDomainWithUserLatLng(
            domainPlace: SimplePlace,
            userLatLng: LatLng
        ): UISimplePlace = UISimplePlace(
            name = domainPlace.name,
            formattedDistanceFromUser = "${userLatLng.distanceTo(domainPlace.latLng)} meters away"
        )
    }
}