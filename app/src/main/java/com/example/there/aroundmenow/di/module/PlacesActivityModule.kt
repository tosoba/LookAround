package com.example.there.aroundmenow.di.module

import com.example.there.aroundmenow.places.PlacesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PlacesActivityModule {
    @ContributesAndroidInjector
    abstract fun placesActivity(): PlacesActivity
}