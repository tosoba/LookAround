package com.example.domain.repo

@Suppress("unused")
sealed class Result<V, E> {
    data class Value<V, E>(val value: V) : Result<V, E>() {
        fun <E1> withErrorType(): Result<V, E1> = Result.Value(value)
    }

    data class Error<V, E>(val error: E) : Result<V, E>() {
        fun <E1> withError(error: E1): Result<V, E1> = Result.Error(error)
    }
}