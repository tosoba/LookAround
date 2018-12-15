package com.example.there.aroundmenow.base.architecture.vm

import io.reactivex.Observable

interface ObservableStateHolder<State> {
    val observableState: Observable<State>
}