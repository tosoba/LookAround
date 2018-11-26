package com.example.there.aroundmenow.di.fragment.places.placetypes

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.places.placetypes.PlaceTypesActions
import com.example.there.aroundmenow.places.placetypes.PlaceTypesActionsExecutor
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.placetypes.PlaceTypesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PlaceTypesModule {

    @PlaceTypesScope
    @Binds
    abstract fun PlaceTypesPresenter(actionsExecutor: PlaceTypesActionsExecutor): PlaceTypesActions

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