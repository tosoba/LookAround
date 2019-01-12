package com.example.there.lookaround.base.architecture.vm

import io.reactivex.Observable

interface ObservableStateHolder<State> {
    val observableState: Observable<State>
}