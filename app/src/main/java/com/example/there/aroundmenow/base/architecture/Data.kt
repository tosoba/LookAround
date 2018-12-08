package com.example.there.aroundmenow.base.architecture

@Suppress("unused")
sealed class Data<out V, out E> {
    data class Value<out V>(val value: V) : Data<V, Nothing>()
    object Loading : Data<Nothing, Nothing>()
    data class Error<out E>(val error: E) : Data<Nothing, E>()
}

