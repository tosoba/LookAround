package com.example.there.aroundmenow.di.activity

import com.example.there.aroundmenow.di.activity.main.MainActivityModule
import com.example.there.aroundmenow.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity
}