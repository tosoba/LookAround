package com.example.there.aroundmenow.di.activity.main

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.fragment.placedetails.PlaceDetailsModule
import com.example.there.aroundmenow.di.fragment.places.POIsModule
import com.example.there.aroundmenow.di.fragment.places.PlacesModule
import com.example.there.aroundmenow.di.fragment.visualizer.CameraModule
import com.example.there.aroundmenow.di.fragment.visualizer.VisualizerMapModule
import com.example.there.aroundmenow.di.fragment.visualizer.VisualizerModule
import com.example.there.aroundmenow.di.scope.ActivityScope
import com.example.there.aroundmenow.di.scope.ChildFragmentScope
import com.example.there.aroundmenow.di.scope.FragmentScope
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.main.*
import com.example.there.aroundmenow.placedetails.PlaceDetailsFragment
import com.example.there.aroundmenow.places.PlacesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.visualizer.VisualizerFragment
import com.example.there.aroundmenow.visualizer.camera.CameraFragment
import com.example.there.aroundmenow.visualizer.map.VisualizerMapFragment
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