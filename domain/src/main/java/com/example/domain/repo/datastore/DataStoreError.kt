package com.example.domain.repo.datastore

sealed class DataStoreError {
    object Invalid : DataStoreError()
    object Empty : DataStoreError()

    fun <R> toRepositoryResult(
        onDataError: () -> R
    ): R = onDataError()

    fun <R> toRepositoryResult(
        onInvalidData: () -> R,
        onEmptyData: () -> R
    ): R = when (this) {
        is Invalid -> onInvalidData()
        is Empty -> onEmptyData()
    }
}