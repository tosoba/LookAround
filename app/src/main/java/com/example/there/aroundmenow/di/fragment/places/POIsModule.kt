package com.example.there.aroundmenow.di.fragment.places

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.scope.ChildFragmentScope
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.places.pois.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class POIsModule {

    @ChildFragmentScope
    @Binds
    abstract fun poisActions(actionsExecutor: POIsActionsExecutor): POIsActions

    @Binds
    abstract fun poisObservableState(viewModel: POIsViewModel): ObservableStateHolder<POIsState>

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun poisViewModel(
            factory: ViewModelFactory,
            fragment: POIsFragment
        ): POIsViewModel = ViewModelProviders.of(fragment, factory).get(POIsViewModel::class.java)
    }
}