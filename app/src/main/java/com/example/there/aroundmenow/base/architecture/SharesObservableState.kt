package com.example.there.aroundmenow.base.architecture

import io.reactivex.Observable

interface SharesObservableState<State> {
    val observableState: Observable<State>
}