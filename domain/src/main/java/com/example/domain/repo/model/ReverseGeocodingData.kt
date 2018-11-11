package com.example.domain.repo.model

sealed class ReverseGeocodingData {
    class Success(val address: String) : ReverseGeocodingData()
    class GeocodingError(val status: String) : ReverseGeocodingData()
}