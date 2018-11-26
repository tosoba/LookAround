package com.example.there.aroundmenow.di.fragment.places.placetypes

import com.example.there.aroundmenow.places.placetypes.PlaceTypesActions
import com.example.there.aroundmenow.places.placetypes.PlaceTypesActionsExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class PlaceTypesModule {

    @PlaceTypesFragmentScope
    @Binds
    abstract fun PlaceTypesPresenter(actionsExecutor: PlaceTypesActionsExecutor): PlaceTypesActions
}