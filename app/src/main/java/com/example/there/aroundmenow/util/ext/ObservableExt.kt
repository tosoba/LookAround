package com.example.there.aroundmenow.util.ext

import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import io.reactivex.Observable

data class LastTwoValues<T>(val previous: T, val latest: T) {
    companion object {
        fun <T> startingWith(t: T): LastTwoValues<T> = LastTwoValues(t, t)
    }
}

fun <T> Observable<T>.withPreviousValue(
    initial: T
): Observable<LastTwoValues<T>> = scan(LastTwoValues.startingWith(initial)) { last2: LastTwoValues<T>, newValue: T ->
    LastTwoValues(last2.latest, newValue)
}.skip(1).distinctUntilChanged()

fun <V, E> Observable<ViewDataState<V, E>>.valuesOnly(): Observable<ViewDataState.Value<V>> = filter {
    it.hasValue
}.map {
    it as ViewDataState.Value
}
