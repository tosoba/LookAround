package com.example.there.aroundmenow.util.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.there.aroundmenow.util.ext.plusAssign
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class UiDisposablesComponent : LifecycleObserver {

    private val disposables: CompositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() = clear()

    operator fun plusAssign(disposable: Disposable) {
        disposables += disposable
    }

    private fun clear() = disposables.clear()
}