package com.example.there.aroundmenow.di.activity.main

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.activity.ActivityScope
import com.example.there.aroundmenow.di.fragment.places.PlacesModule
import com.example.there.aroundmenow.di.fragment.places.PlacesScope
import com.example.there.aroundmenow.di.fragment.places.placetypes.PlaceTypesModule
import com.example.there.aroundmenow.di.fragment.places.placetypes.PlaceTypesScope
import com.example.there.aroundmenow.di.fragment.places.pois.POIsModule
import com.example.there.aroundmenow.di.fragment.places.pois.POIsScope
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.main.*
import com.example.there.aroundmenow.places.PlacesFragment
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ActivityScope
    @Binds
    abstract fun mainActionsExecutor(executor: MainActionsExecutor): MainActions

    @Binds
    abstract fun mainObservableState(viewModel: MainViewModel): ObservableStateHolder<MainState>

    @POIsScope
    @ContributesAndroidInjector(modules = [POIsModule::class])
    abstract fun poisFragment(): POIsFragment

    @PlaceTypesScope
    @ContributesAndroidInjector(modules = [PlaceTypesModule::class])
    abstract fun placeTypesFragment(): PlaceTypesFragment

    @PlacesScope
    @ContributesAndroidInjector(modules = [PlacesModule::class])
    abstract fun placesFragment(): PlacesFragment

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun mainViewModel(
            factory: ViewModelFactory,
            activity: MainActivity
        ): MainViewModel = ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
    }
}