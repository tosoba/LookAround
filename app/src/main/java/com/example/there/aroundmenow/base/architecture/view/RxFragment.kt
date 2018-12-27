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
) : ViewObservingFragment(), Injectable, StateObserver<State> {

    @Inject
    lateinit var observableStateHolder: ObservableStateHolder<State>

    @Inject
    lateinit var actions: Actions

    override val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableStateHolder.observableState
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
        is HostAware.WithLayout<*, *, *, *> -> inflater.inflate(layoutResource, container, false)
        is HostAware.DataBound<*, *, *, *, *> -> initializeView(layoutResource, inflater, container)
    }

    sealed class HostUnaware<State : Any, Actions : Any>(
        @LayoutRes layoutResource: Int
    ) : RxFragment<State, Actions>(layoutResource) {

        abstract class WithLayout<State : Any, Actions : Any>(
            @LayoutRes layoutResource: Int
        ) : HostUnaware<State, Actions>(layoutResource)

        abstract class DataBound<State : Any, Actions : Any, Binding : ViewDataBinding>(
            @LayoutRes layoutResource: Int
        ) : HostUnaware<State, Actions>(layoutResource), FragmentBindingInitializer<Binding>
    }

    sealed class HostAware<State : Any, ActivityState : Any, ParentFragmentState : Any, Actions : Any>(
        @LayoutRes layoutResource: Int,
        private val mode: HostAwarenessMode
    ) : RxFragment<State, Actions>(layoutResource), HostStateObserver<ActivityState, ParentFragmentState> {

        enum class HostAwarenessMode {
            ACTIVITY_ONLY, PARENT_FRAGMENT_ONLY, BOTH
        }

        @Suppress("UNCHECKED_CAST")
        protected val observableActivityState: Observable<ActivityState>
            get() {
                val hostActivity = activity
                return if (hostActivity != null && hostActivity is RxActivity<*, *>)
                    hostActivity.observableStateHolder.observableState.map { it as ActivityState }
                else throw IllegalArgumentException("RxActivity State type error.")
            }

        @Suppress("UNCHECKED_CAST")
        protected val observableParentFragmentState: Observable<ParentFragmentState>
            get() {
                val parent = parentFragment
                return if (parent != null && parent is RxFragment<*, *>)
                    parent.observableStateHolder.observableState.map { it as ParentFragmentState }
                else throw  IllegalArgumentException("RxFragment State type error.")
            }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            if (mode == HostAwarenessMode.ACTIVITY_ONLY || mode == HostAwarenessMode.BOTH)
                observableActivityState.observeOn(AndroidSchedulers.mainThread())
                    .observeActivity()

            if (mode == HostAwarenessMode.PARENT_FRAGMENT_ONLY || mode == HostAwarenessMode.BOTH)
                observableParentFragmentState.observeOn(AndroidSchedulers.mainThread())
                    .observeParentFragment()
        }

        abstract class WithLayout<State : Any, ActivityState : Any, ParentFragmentState : Any, Actions : Any>(
            @LayoutRes layoutResource: Int,
            mode: HostAwarenessMode
        ) : HostAware<State, ActivityState, ParentFragmentState, Actions>(layoutResource, mode)

        abstract class DataBound<State : Any, ActivityState : Any, ParentFragmentState : Any, Actions : Any, Binding : ViewDataBinding>(
            @LayoutRes layoutResource: Int,
            mode: HostAwarenessMode
        ) : HostAware<State, ActivityState, ParentFragmentState, Actions>(layoutResource, mode),
            FragmentBindingInitializer<Binding>
    }
}