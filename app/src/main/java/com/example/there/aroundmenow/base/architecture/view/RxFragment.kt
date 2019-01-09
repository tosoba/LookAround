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

sealed class RxFragment(
    @LayoutRes private val layoutResource: Int,
    viewDisposalMode: UiDisposablesComponent.DisposalMode = UiDisposablesComponent.DisposalMode.ON_PAUSE
) : ViewObservingFragment(viewDisposalMode) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = when (this) {
        is Stateless.HostUnaware.WithLayout -> inflater.inflate(layoutResource, container, false)
        is Stateless.HostUnaware.DataBound<*> -> initializeView(layoutResource, inflater, container)
        is Stateless.HostAware.WithLayout<*, *> -> inflater.inflate(layoutResource, container, false)
        is Stateless.HostAware.DataBound<*, *, *> -> initializeView(layoutResource, inflater, container)
        is Stateful.HostUnaware.WithLayout<*, *> -> inflater.inflate(layoutResource, container, false)
        is Stateful.HostUnaware.DataBound<*, *, *> -> initializeView(layoutResource, inflater, container)
        is Stateful.HostAware.WithLayout<*, *, *, *> -> inflater.inflate(layoutResource, container, false)
        is Stateful.HostAware.DataBound<*, *, *, *, *> -> initializeView(layoutResource, inflater, container)
    }

    enum class HostAwarenessMode {
        ACTIVITY_ONLY, PARENT_FRAGMENT_ONLY, BOTH
    }

    sealed class Stateless(
        @LayoutRes layoutResource: Int
    ) : RxFragment(layoutResource) {

        sealed class HostUnaware(
            @LayoutRes layoutResource: Int
        ) : Stateless(layoutResource) {

            abstract class WithLayout(
                @LayoutRes layoutResource: Int
            ) : HostUnaware(layoutResource)

            abstract class DataBound<Binding : ViewDataBinding>(
                @LayoutRes layoutResource: Int
            ) : HostUnaware(layoutResource), FragmentBindingInitializer<Binding>
        }

        sealed class HostAware<ActivityState : Any, ParentFragmentState : Any>(
            @LayoutRes layoutResource: Int,
            private val mode: HostAwarenessMode
        ) : Stateless(layoutResource), HostStateObserver<ActivityState, ParentFragmentState> {

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
                    return if (parent != null && parent is Stateful<*, *>)
                        parent.observableStateHolder.observableState.map { it as ParentFragmentState }
                    else throw  IllegalArgumentException("RxFragment State type error.")
                }

            override fun onResume() {
                super.onResume()

                if (mode == HostAwarenessMode.ACTIVITY_ONLY || mode == HostAwarenessMode.BOTH)
                    observableActivityState.observeOn(AndroidSchedulers.mainThread())
                        .observeActivity()

                if (mode == HostAwarenessMode.PARENT_FRAGMENT_ONLY || mode == HostAwarenessMode.BOTH)
                    observableParentFragmentState.observeOn(AndroidSchedulers.mainThread())
                        .observeParentFragment()
            }

            abstract class WithLayout<ActivityState : Any, ParentFragmentState : Any>(
                @LayoutRes layoutResource: Int,
                mode: HostAwarenessMode
            ) : HostAware<ActivityState, ParentFragmentState>(layoutResource, mode)

            abstract class DataBound<ActivityState : Any, ParentFragmentState : Any, Binding : ViewDataBinding>(
                @LayoutRes layoutResource: Int,
                mode: HostAwarenessMode
            ) : HostAware<ActivityState, ParentFragmentState>(layoutResource, mode),
                FragmentBindingInitializer<Binding>
        }
    }

    sealed class Stateful<State : Any, Actions : Any>(
        @LayoutRes layoutResource: Int
    ) : RxFragment(layoutResource), Injectable, StateObserver<State> {

        @Inject
        lateinit var observableStateHolder: ObservableStateHolder<State>

        @Inject
        lateinit var actions: Actions

        override fun onResume() {
            super.onResume()
            observableStateHolder.observableState
                .observeOn(AndroidSchedulers.mainThread())
                .observe()
        }

        sealed class HostUnaware<State : Any, Actions : Any>(
            @LayoutRes layoutResource: Int
        ) : Stateful<State, Actions>(layoutResource) {

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
        ) : Stateful<State, Actions>(layoutResource), HostStateObserver<ActivityState, ParentFragmentState> {

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
                    return if (parent != null && parent is Stateful<*, *>)
                        parent.observableStateHolder.observableState.map { it as ParentFragmentState }
                    else throw  IllegalArgumentException("RxFragment State type error.")
                }

            override fun onResume() {
                super.onResume()

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
}