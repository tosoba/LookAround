package com.example.domain.repo.model

import com.google.android.gms.maps.model.LatLng

data class GeocodingInfo(
    val latLng: LatLng,
    val address: String
)