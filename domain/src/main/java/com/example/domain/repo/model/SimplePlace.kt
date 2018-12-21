package com.example.domain.repo.model

import com.google.android.gms.maps.model.LatLng

data class SimplePlace(
    val latLng: LatLng,
    val name: String
)