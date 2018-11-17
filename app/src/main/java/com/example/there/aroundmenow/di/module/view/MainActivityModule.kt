package com.example.there.aroundmenow.di.module.view

import com.example.there.aroundmenow.main.MainActivity
import dagger.Module

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}