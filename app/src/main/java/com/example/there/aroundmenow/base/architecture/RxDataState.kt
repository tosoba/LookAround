package com.example.there.aroundmenow.base.architecture

@Suppress("unused")
sealed class RxDataState<out V, out E> {
    data class Value<out V>(val value: V) : RxDataState<V, Nothing>()
    object Loading : RxDataState<Nothing, Nothing>()
    data class Error<out E>(val error: E) : RxDataState<Nothing, E>()
}

