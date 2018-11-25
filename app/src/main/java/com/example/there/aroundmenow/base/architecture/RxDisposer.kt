package com.example.there.aroundmenow.base.architecture

import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import io.reactivex.disposables.Disposable

interface RxDisposer {
    val uiDisposables: UiDisposablesComponent

    fun Disposable.disposeOnDestroy() {
        uiDisposables += this
    }
}