package com.example.domain.task.error

sealed class FindPlaceDetailsError {
    data class Exception(val throwable: Throwable) : FindPlaceDetailsError()
    object PlaceDetailsNotFound : FindPlaceDetailsError()
}