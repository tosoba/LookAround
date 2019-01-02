package com.example.domain.task.error

sealed class FindPlacePhotosError {
    data class Exception(val throwable: Throwable) : FindPlacePhotosError()
    object NoPhotosFound : FindPlacePhotosError()
}