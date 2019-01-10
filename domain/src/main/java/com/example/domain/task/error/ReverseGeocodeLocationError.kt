package com.example.domain.task.error

sealed class ReverseGeocodeLocationError {
    object GeocodingFailed : ReverseGeocodeLocationError()
    class Exception(val throwable: Throwable) : ReverseGeocodeLocationError()
}