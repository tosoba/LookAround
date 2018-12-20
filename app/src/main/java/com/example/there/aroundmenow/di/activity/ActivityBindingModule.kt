package com.example.there.aroundmenow.di.activity

import com.example.there.aroundmenow.di.activity.main.MainModule
import com.example.there.aroundmenow.di.scope.ActivityScope
import com.example.there.aroundmenow.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity
}