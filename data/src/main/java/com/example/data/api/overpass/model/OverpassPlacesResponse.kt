package com.example.data.api.overpass.model

import com.google.gson.annotations.SerializedName

class OverpassPlacesResponse(
    @SerializedName("elements") val places: List<OverpassPlace>
)