package com.example.there.aroundmenow.di.fragment.visualizer

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.scope.ChildFragmentScope
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.visualizer.map.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class VisualizerMapModule {

    @ChildFragmentScope
    @Binds
    abstract fun visualizerMapActions(actionsExecutor: VisualizerMapActionsExecutor): VisualizerMapActions

    @Binds
    abstract fun visualizerMapViewObservableState(viewModel: VisualizerMapViewModel): ObservableStateHolder<VisualizerMapState>

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun visualizerMapViewModel(
            factory: ViewModelFactory,
            fragment: VisualizerMapFragment
        ): VisualizerMapViewModel = ViewModelProviders.of(fragment, factory).get(VisualizerMapViewModel::class.java)
    }
}