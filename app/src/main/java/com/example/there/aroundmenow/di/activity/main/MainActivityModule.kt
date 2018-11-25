package com.example.there.aroundmenow.di.activity.main

import com.example.there.aroundmenow.di.activity.ActivityScope
import com.example.there.aroundmenow.di.fragment.pois.POIsFragmentModule
import com.example.there.aroundmenow.di.fragment.pois.POIsFragmentScope
import com.example.there.aroundmenow.main.MainActions
import com.example.there.aroundmenow.main.MainActionsExecutor
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
}