package com.example.there.aroundmenow.di.module

import com.example.there.aroundmenow.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PlacesActivityModule {
    @ContributesAndroidInjector
    abstract fun placesActivity(): MainActivity
}