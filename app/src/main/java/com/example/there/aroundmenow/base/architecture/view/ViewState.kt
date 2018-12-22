package com.example.there.aroundmenow.base.architecture.view

import com.example.domain.repo.Result
import io.reactivex.functions.Consumer

@Suppress("unused")
sealed class ViewDataState<out V, out E> {
    object Idle : ViewDataState<Nothing, Nothing>()
    data class Value<out V>(val value: V) : ViewDataState<V, Nothing>()
    object Loading : ViewDataState<Nothing, Nothing>()
    data class Error<out E>(val error: E) : ViewDataState<Nothing, E>()

    val hasValue: Boolean
        get() = this is ViewDataState.Value<*>
}

sealed class ViewLoadingState {
    object Idle : ViewLoadingState()
    object Loading : ViewLoadingState()
}

interface ViewDataSideEffect<V, E> {
    fun onValue(value: V) = Unit
    fun onError(error: E) = Unit
    fun onLoading() = Unit

    val consumer: Consumer<Result<V, E>>
        get() = Consumer {
            when (it) {
                is Result.Value -> onValue(it.value)
                is Result.Error -> onError(it.error)
            }
        }
}
