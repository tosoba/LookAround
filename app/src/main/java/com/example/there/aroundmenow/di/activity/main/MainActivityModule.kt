package com.example.there.aroundmenow.di.activity.main

import com.example.there.aroundmenow.di.activity.ActivityScope
import com.example.there.aroundmenow.main.MainActions
import com.example.there.aroundmenow.main.MainActionsExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @ActivityScope
    @Binds
    abstract fun mainActionsExecutor(executor: MainActionsExecutor): MainActions
}