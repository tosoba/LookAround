package com.example.domain.task.error

sealed class ReverseGeocodeLocationError {
    data class Exception(val throwable: Throwable): ReverseGeocodeLocationError()
    object GeocodingError: ReverseGeocodeLocationError()
}