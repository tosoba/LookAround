package com.example.there.lookaround.base.architecture.vm

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class RxViewModel<State>(initialState: State) : ViewModel(), ObservableStateHolder<State> {

    val state = BehaviorRelay.createDefault(initialState)!!

    override val observableState: Observable<State>
        get() = state

    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}