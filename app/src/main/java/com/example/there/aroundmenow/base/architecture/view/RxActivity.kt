package com.example.there.aroundmenow.base.architecture.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

sealed class RxActivity<State : Any, Actions : Any>(
    @LayoutRes protected val layoutResource: Int
) : ViewObservingActivity(), ViewObserver, HasSupportFragmentInjector, StateObserver<State> {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var observableStateHolder: ObservableStateHolder<State>

    @Inject
    lateinit var actions: Actions

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun onStart() {
        super.onStart()
        observableStateHolder.observableState.observe()
    }

    abstract class Layout<State : Any, Actions : Any>(
        @LayoutRes layoutResource: Int
    ) : RxActivity<State, Actions>(layoutResource) {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(layoutResource)
        }
    }

    abstract class DataBound<State : Any, Actions : Any, Binding : ViewDataBinding>(
        @LayoutRes layoutResource: Int
    ) : RxActivity<State, Actions>(layoutResource),
        BindingInitializer<Binding> {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            DataBindingUtil.setContentView<Binding>(this, layoutResource).apply {
                setLifecycleOwner(this@DataBound)
                init()
            }
        }
    }
}