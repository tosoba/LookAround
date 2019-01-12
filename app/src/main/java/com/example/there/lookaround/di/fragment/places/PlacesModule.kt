package com.example.there.lookaround.di.fragment.places

import androidx.lifecycle.ViewModelProviders
import com.example.there.lookaround.base.architecture.vm.ObservableStateHolder
import com.example.there.lookaround.di.scope.FragmentScope
import com.example.there.lookaround.di.vm.ViewModelFactory
import com.example.there.lookaround.places.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PlacesModule {

    @FragmentScope
    @Binds
    abstract fun placesActions(actionsExecutor: PlacesActionsExecutor): PlacesActions

    @Binds
    abstract fun placesObservableState(viewModel: PlacesViewModel): ObservableStateHolder<PlacesState>

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun placesViewModel(
            factory: ViewModelFactory,
            fragment: PlacesFragment
        ): PlacesViewModel = ViewModelProviders.of(fragment, factory).get(PlacesViewModel::class.java)
    }
}