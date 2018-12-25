package com.example.there.aroundmenow.di.fragment.visualizer

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.scope.FragmentScope
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.visualizer.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class VisualizerModule {

    @FragmentScope
    @Binds
    abstract fun visualizerActions(actionsExecutor: VisualizerActionsExecutor): VisualizerActions

    @Binds
    abstract fun visualizerObservableState(viewModel: VisualizerViewModel): ObservableStateHolder<VisualizerState>

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun visualizerViewModel(
            factory: ViewModelFactory,
            fragment: VisualizerFragment
        ): VisualizerViewModel = ViewModelProviders.of(fragment, factory).get(VisualizerViewModel::class.java)
    }
}