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

abstract class RxActivity<State, VM, Presenter>(
    private val viewModelClass: Class<VM>
) : AppCompatActivity(), HasSupportFragmentInjector where VM : RxViewModel<State>, Presenter : RxPresenter<State, VM> {

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
    lateinit var presenter: Presenter

    protected val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()
        presenter.viewModel = viewModel
        lifecycle.addObserver(uiDisposables)
        observeState()
    }

    override fun onDestroy() {
        uiDisposables.clear()
        super.onDestroy()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    abstract fun initializeLayout()

    abstract fun observeState()
}