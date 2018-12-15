package com.example.there.aroundmenow.di.fragment.places.placetypes

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.places.placetypes.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PlaceTypesModule {

    @PlaceTypesScope
    @Binds
    abstract fun placeTypesPresenter(actionsExecutor: PlaceTypesActionsExecutor): PlaceTypesActions

    @Binds
    abstract fun placeTypesObservableState(viewModel: PlaceTypesViewModel): ObservableStateHolder<PlaceTypesState>

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun placeTypesViewModel(
            factory: ViewModelFactory,
            fragment: PlaceTypesFragment
        ): PlaceTypesViewModel = ViewModelProviders.of(fragment, factory).get(PlaceTypesViewModel::class.java)
    }
}