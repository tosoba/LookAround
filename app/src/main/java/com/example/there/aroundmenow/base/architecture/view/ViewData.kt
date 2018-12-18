package com.example.there.aroundmenow.base.architecture.view

@Suppress("unused")
sealed class ViewData<out V, out E> {
    object Idle : ViewData<Nothing, Nothing>()
    data class Value<out V>(val value: V) : ViewData<V, Nothing>()
    object Loading : ViewData<Nothing, Nothing>()
    data class Error<out E>(val error: E) : ViewData<Nothing, E>()
}

