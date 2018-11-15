package com.example.there.aroundmenow.base.architecture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.di.Injectable
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import io.reactivex.Observable
import javax.inject.Inject

abstract class RxFragment<ActivityState, State, VM, Presenter>(
    private val viewModelClass: Class<VM>,
    private val activityStateClass: Class<ActivityState>
) : Fragment(), Injectable where VM : RxViewModel<State>, Presenter : RxPresenter<State, VM> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    protected val observableState: Observable<State>
        get() = viewModel.observableState

    protected val observableActivityState: Observable<ActivityState>?
        get() {
            val hostActivity = activity
            return if (hostActivity != null && hostActivity is RxActivity<*, *, *>) hostActivity.observableState.cast(
                activityStateClass
            )
            else null
        }

    @Inject
    lateinit var presenter: Presenter

    protected val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.viewModel = viewModel
        lifecycle.addObserver(uiDisposables)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    abstract fun observeState()
}