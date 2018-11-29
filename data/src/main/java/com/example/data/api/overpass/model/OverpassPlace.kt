package com.example.data.api.overpass.model

import com.google.gson.annotations.SerializedName

data class OverpassPlace(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
    val tags: OverpassPlaceTags
)