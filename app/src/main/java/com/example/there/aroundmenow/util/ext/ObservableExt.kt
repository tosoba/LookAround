package com.example.there.aroundmenow.util.ext

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
