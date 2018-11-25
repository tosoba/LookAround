package com.example.there.aroundmenow.di.fragment

import com.example.there.aroundmenow.di.fragment.pois.POIsFragmentModule
import com.example.there.aroundmenow.places.pois.POIsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [POIsFragmentModule::class])
    abstract fun poisFragment(): POIsFragment
}