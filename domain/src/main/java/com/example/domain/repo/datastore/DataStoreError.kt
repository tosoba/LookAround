package com.example.domain.repo.datastore

sealed class DataStoreError {
    data class Exception(val throwable: Throwable) : DataStoreError()
    sealed class Data : DataStoreError() {
        object Invalid : Data()
        object Empty : Data()
    }
}