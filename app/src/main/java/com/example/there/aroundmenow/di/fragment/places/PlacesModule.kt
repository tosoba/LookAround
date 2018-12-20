package com.example.there.aroundmenow.di.fragment.places

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.scope.FragmentScope
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.places.*
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