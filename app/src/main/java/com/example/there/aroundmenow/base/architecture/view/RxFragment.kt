package com.example.there.aroundmenow.base.architecture.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.Injectable
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

sealed class RxFragment<State : Any, Actions : Any>(
    @LayoutRes protected val layoutResource: Int
) : ViewObservingFragment(), Injectable,
    StateObserver<State> {

    @Inject
    lateinit var observableStateHolderSharer: ObservableStateHolder<State>

    @Inject
    lateinit var actions: Actions

    override val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableStateHolderSharer.observableState
            .observeOn(AndroidSchedulers.mainThread())
            .observe()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = when (this) {
        is HostUnaware.WithLayout<*, *> -> inflater.inflate(layoutResource, container, false)
        is HostUnaware.DataBound<*, *, *> -> initializeView(layoutResource, inflater, container)
        is HostAware.WithLayout<*, *, *> -> inflater.inflate(layoutResource, container, false)
        is HostAware.DataBound<*, *, *, *> -> initializeView(layoutResource, inflater, container)
    }

    sealed class HostUnaware<State : Any, Actions : Any>(
        @LayoutRes layoutResource: Int
    ) : RxFragment<State, Actions>(layoutResource) {

        abstract class WithLayout<State : Any, Actions : Any>(
            @LayoutRes layoutResource: Int
        ) : HostUnaware<State, Actions>(layoutResource)

        abstract class DataBound<State : Any, Actions : Any, Binding : ViewDataBinding>(
            @LayoutRes layoutResource: Int
        ) : HostUnaware<State, Actions>(layoutResource),
            FragmentBindingInitializer<Binding>
    }

    sealed class HostAware<State : Any, HostState : Any, Actions : Any>(
        @LayoutRes layoutResource: Int
    ) : RxFragment<State, Actions>(layoutResource),
        HostStateObserver<HostState> {

        @Suppress("UNCHECKED_CAST")
        private val observableActivityState: Observable<HostState>
            get() {
                val hostActivity = activity
                return if (hostActivity != null && hostActivity is RxActivity<*, *>)
                    hostActivity.observableStateHolderSharer.observableState.map { it as HostState }
                else throw IllegalArgumentException("RxActivity State type error.")
            }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            observableActivityState.observeOn(AndroidSchedulers.mainThread())
                .observeHost()
        }

        abstract class WithLayout<State : Any, HostState : Any, Actions : Any>(
            @LayoutRes layoutResource: Int
        ) : HostAware<State, HostState, Actions>(layoutResource)

        abstract class DataBound<State : Any, HostState : Any, Actions : Any, Binding : ViewDataBinding>(
            @LayoutRes layoutResource: Int
        ) : HostAware<State, HostState, Actions>(layoutResource),
            FragmentBindingInitializer<Binding>
    }
}