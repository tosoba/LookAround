package com.example.there.aroundmenow.base.architecture

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.di.Injectable
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import io.reactivex.Observable
import javax.inject.Inject

abstract class RxFragment<State, Actions : Any> : Fragment(), ObservesState, Injectable, RxDisposer {

    @Inject
    lateinit var observableStateSharer: SharesObservableState<State>

    val observableState: Observable<State>
        get() = observableStateSharer.observableState

    @Suppress("UNCHECKED_CAST")
    protected fun <ActivityState> observableActivityState(): Observable<ActivityState>? {
        val hostActivity = activity
        return if (hostActivity != null && hostActivity is RxActivity<*, *>)
            hostActivity.observableState.map { it as ActivityState }
        else null
    }

    @Inject
    lateinit var actions: Actions

    override val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeState()
    }
}