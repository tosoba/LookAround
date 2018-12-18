package com.example.domain.task.error


sealed class FindNearbyPOIsError {
    data class Exception(val throwable: Throwable): FindNearbyPOIsError()
    object NoPOIsFound: FindNearbyPOIsError()
    object UserLocationUnknown: FindNearbyPOIsError()
}