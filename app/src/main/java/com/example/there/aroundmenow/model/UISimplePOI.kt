package com.example.there.aroundmenow.model

import com.example.domain.repo.model.SimplePOI
import com.example.there.aroundmenow.util.ext.distanceTo
import com.google.android.gms.maps.model.LatLng

data class UISimplePOI(
    val name: String,
    val formattedDistanceFromUser: String
) {
    companion object {
        fun fromDomainWithUserLatLng(domainPOI: SimplePOI, userLatLng: LatLng): UISimplePOI = UISimplePOI(
            name = domainPOI.name,
            formattedDistanceFromUser = "${userLatLng.distanceTo(domainPOI.latLng)} meters away"
        )
    }
}