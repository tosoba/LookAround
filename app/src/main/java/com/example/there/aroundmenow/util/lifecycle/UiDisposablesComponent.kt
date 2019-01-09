package com.example.there.aroundmenow.util.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.there.aroundmenow.util.ext.plusAssign
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class UiDisposablesComponent(
    private val disposalMode: DisposalMode
) : LifecycleObserver {

    private val disposables: CompositeDisposable = CompositeDisposable()

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (disposalMode == DisposalMode.ON_PAUSE) clear()
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (disposalMode == DisposalMode.ON_DESTROY) clear()
    }

    operator fun plusAssign(disposable: Disposable) {
        disposables += disposable
    }

    private fun clear() = disposables.clear()

    enum class DisposalMode {
        ON_PAUSE, ON_DESTROY
    }
}