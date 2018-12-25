package com.example.domain.task.error


sealed class FindNearbyPlacesError {
    data class Exception(val throwable: Throwable): FindNearbyPlacesError()
    object NoPlacesFound: FindNearbyPlacesError()
    object UserLocationUnknown: FindNearbyPlacesError()
}