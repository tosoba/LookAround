package com.example.data.api.model

class ReverseGeocodingResponse(
    val status: String,
    val results: List<ReverseGeocodingResult>
) {
    val hasResults: Boolean
        get() = status == OK_STATUS && results.isNotEmpty()

    companion object {
        const val OK_STATUS = "OK"
    }
}

class ReverseGeocodingResult(
    val formattedAddress: String,
    val geometry: ReverseGeocodingGeometry
)

class ReverseGeocodingGeometry(
    val location: ReverseGeocodingLocation
)

class ReverseGeocodingLocation(
    val lat: Double,
    val lng: Double
)