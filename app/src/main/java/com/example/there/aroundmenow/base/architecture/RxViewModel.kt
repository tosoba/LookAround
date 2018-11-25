package com.example.there.aroundmenow.base.architecture

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class RxViewModel<State>(initialState: State) : ViewModel() {

    val state = BehaviorRelay.createDefault(initialState)!!

    val observableState: Observable<State>
        get() = state

    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}