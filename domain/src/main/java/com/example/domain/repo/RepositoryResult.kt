package com.example.domain.repo

sealed class RepositoryResult<V> {
    data class Value<V>(val value: V) : RepositoryResult<V>()
    data class Error<V>(val throwable: Throwable) : RepositoryResult<V>()
}