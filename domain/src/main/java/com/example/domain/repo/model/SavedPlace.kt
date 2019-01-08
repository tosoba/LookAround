package com.example.domain.repo.model

import com.google.android.gms.maps.model.LatLng

data class SavedPlace(
    val id: String,
    val name: String,
    val latLng: LatLng,
    val phoneNumber: String?,
    val websiteUri: String?,
    val rating: Float,
    val placeTypes: List<Int>
)