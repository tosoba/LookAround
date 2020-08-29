package com.example.domain.repo

@Suppress("unused")
sealed class Result<V, E> {
    data class Value<V, E>(val value: V) : Result<V, E>() {
        fun <E1> mapToType(): Result<V, E1> = Value(value)
    }

    data class Error<V, E>(val error: E) : Result<V, E>() {
        fun <E1> mapTo(error: E1): Result<V, E1> = Error(error)
    }
}