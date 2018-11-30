package com.example.domain.repo.datastore

@Suppress("unused")
sealed class DataStoreResult<out V> {
    data class Value<out V>(val value: V) : DataStoreResult<V>()
    object Invalid : DataStoreResult<Nothing>()
    object Empty : DataStoreResult<Nothing>()
    data class Error(val throwable: Throwable) : DataStoreResult<Nothing>()
}