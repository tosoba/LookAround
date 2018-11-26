package com.example.there.aroundmenow.base.architecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import javax.inject.Inject

abstract class RxActivity<State, VM, Actions : Any>(
    private val viewModelClass: Class<VM>
) : AppCompatActivity(), ObservesState, RxDisposer, HasSupportFragmentInjector
        where VM : RxViewModel<State> {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModel: VM

    val observableState: Observable<State>
        get() = viewModel.observableState

    @Inject
    lateinit var actions: Actions

    override val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()
        lifecycle.addObserver(uiDisposables)
        observeState()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    abstract fun initializeLayout()
}