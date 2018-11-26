package com.example.there.aroundmenow.di.activity.main

import com.example.there.aroundmenow.di.activity.ActivityScope
import com.example.there.aroundmenow.di.fragment.places.placetypes.PlaceTypesFragmentScope
import com.example.there.aroundmenow.di.fragment.places.placetypes.PlaceTypesModule
import com.example.there.aroundmenow.di.fragment.places.pois.POIsFragmentModule
import com.example.there.aroundmenow.di.fragment.places.pois.POIsFragmentScope
import com.example.there.aroundmenow.main.MainActions
import com.example.there.aroundmenow.main.MainActionsExecutor
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ActivityScope
    @Binds
    abstract fun mainActionsExecutor(executor: MainActionsExecutor): MainActions

    @POIsFragmentScope
    @ContributesAndroidInjector(modules = [POIsFragmentModule::class])
    abstract fun poisFragment(): POIsFragment

    @PlaceTypesFragmentScope
    @ContributesAndroidInjector(modules = [PlaceTypesModule::class])
    abstract fun placeTypesFragment(): PlaceTypesFragment
}