package com.example.there.aroundmenow.di.fragment.places.pois

import com.example.there.aroundmenow.places.pois.POIsActions
import com.example.there.aroundmenow.places.pois.POIsActionsExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class POIsFragmentModule {

    @POIsFragmentScope
    @Binds
    abstract fun poisPresenter(actionsExecutor: POIsActionsExecutor): POIsActions
}