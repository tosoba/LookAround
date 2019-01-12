package com.example.there.lookaround.di.activity.main

import androidx.lifecycle.ViewModelProviders
import com.example.there.lookaround.base.architecture.vm.ObservableStateHolder
import com.example.there.lookaround.di.fragment.placedetails.PlaceDetailsModule
import com.example.there.lookaround.di.fragment.places.FavouritesModule
import com.example.there.lookaround.di.fragment.places.POIsModule
import com.example.there.lookaround.di.fragment.places.PlacesModule
import com.example.there.lookaround.di.fragment.visualizer.CameraModule
import com.example.there.lookaround.di.fragment.visualizer.VisualizerMapModule
import com.example.there.lookaround.di.fragment.visualizer.VisualizerModule
import com.example.there.lookaround.di.scope.ActivityScope
import com.example.there.lookaround.di.scope.ChildFragmentScope
import com.example.there.lookaround.di.scope.FragmentScope
import com.example.there.lookaround.di.vm.ViewModelFactory
import com.example.there.lookaround.main.*
import com.example.there.lookaround.placedetails.PlaceDetailsFragment
import com.example.there.lookaround.places.PlacesFragment
import com.example.there.lookaround.places.favourites.FavouritesFragment
import com.example.there.lookaround.places.pois.POIsFragment
import com.example.there.lookaround.visualizer.VisualizerFragment
import com.example.there.lookaround.visualizer.camera.CameraFragment
import com.example.there.lookaround.visualizer.map.VisualizerMapFragment
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

    @FragmentScope
    @ContributesAndroidInjector(modules = [PlacesModule::class])
    abstract fun placesFragment(): PlacesFragment

    @ChildFragmentScope
    @ContributesAndroidInjector(modules = [POIsModule::class])
    abstract fun poisFragment(): POIsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [PlaceDetailsModule::class])
    abstract fun placeDetailsFragment(): PlaceDetailsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [VisualizerModule::class])
    abstract fun visualizerFragment(): VisualizerFragment

    @ChildFragmentScope
    @ContributesAndroidInjector(modules = [CameraModule::class])
    abstract fun cameraFragment(): CameraFragment

    @ChildFragmentScope
    @ContributesAndroidInjector(modules = [VisualizerMapModule::class])
    abstract fun visualizerMapFragment(): VisualizerMapFragment

    @ChildFragmentScope
    @ContributesAndroidInjector(modules = [FavouritesModule::class])
    abstract fun favouritesFragment(): FavouritesFragment

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