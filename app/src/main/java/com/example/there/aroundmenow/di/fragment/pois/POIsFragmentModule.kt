package com.example.there.aroundmenow.di.fragment.pois

import com.example.there.aroundmenow.di.fragment.FragmentScope
import com.example.there.aroundmenow.places.pois.POIsActions
import com.example.there.aroundmenow.places.pois.POIsActionsExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class POIsFragmentModule {

    @FragmentScope
    @Binds
    abstract fun poisPresenter(actionsExecutor: POIsActionsExecutor): POIsActions
}