package com.example.there.aroundmenow.model

import com.example.domain.repo.model.GeocodingInfo


data class UIGeocodingInfo(
    val formattedCoordinates: String,
    val formattedAddress: String
) {
    companion object {
        fun fromDomain(domain: GeocodingInfo): UIGeocodingInfo = UIGeocodingInfo(
            formattedAddress = domain.address,
            formattedCoordinates = domain.latLng.toString()
        )
    }
}