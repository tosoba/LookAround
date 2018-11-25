package com.example.there.aroundmenow.base.architecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import javax.inject.Inject

abstract class RxActivity<State, VM, Actions>(
    private val viewModelClass: Class<VM>
) : AppCompatActivity(), ObservesState, RxDisposer, HasSupportFragmentInjector
        where VM : RxViewModel<State>, Actions : RxViewModelHolder<State, VM> {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    val observableState: Observable<State>
        get() = viewModel.observableState

    @Inject
    lateinit var actions: Actions

    override val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()
        actions.viewModel = viewModel
        lifecycle.addObserver(uiDisposables)
        observeState()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    abstract fun initializeLayout()
}