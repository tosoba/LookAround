package com.example.domain.repo.datastore

sealed class DataStoreError {
    data class Exception(val throwable: Throwable) : DataStoreError()

    sealed class Data : DataStoreError() {
        object Invalid : Data()
        object Empty : Data()
    }

    fun <R> toResult(
        onException: (Throwable) -> R,
        onDataError: () -> R
    ): R = when (this) {
        is Exception -> onException(throwable)
        is Data -> onDataError()
    }

    fun <R> toResult(
        onException: (Throwable) -> R,
        onInvalidData: () -> R,
        onEmptyData: () -> R
    ): R = when (this) {
        is Exception -> onException(throwable)
        is Data.Invalid -> onInvalidData()
        is Data.Empty -> onEmptyData()
    }
}