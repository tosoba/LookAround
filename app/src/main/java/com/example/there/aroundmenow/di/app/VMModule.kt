package com.example.there.aroundmenow.di.app

import androidx.lifecycle.ViewModel
import com.example.there.aroundmenow.di.vm.ViewModelKey
import com.example.there.aroundmenow.main.MainViewModel
import com.example.there.aroundmenow.placedetails.PlaceDetailsViewModel
import com.example.there.aroundmenow.places.PlacesViewModel
import com.example.there.aroundmenow.places.favourites.FavouritesViewModel
import com.example.there.aroundmenow.places.pois.POIsViewModel
import com.example.there.aroundmenow.visualizer.VisualizerViewModel
import com.example.there.aroundmenow.visualizer.camera.CameraViewModel
import com.example.there.aroundmenow.visualizer.map.VisualizerMapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VMModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    abstract fun placesViewModel(viewModel: PlacesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(POIsViewModel::class)
    abstract fun poisViewModel(viewModel: POIsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaceDetailsViewModel::class)
    abstract fun placeDetailsViewModel(viewModel: PlaceDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VisualizerViewModel::class)
    abstract fun visualizerViewModel(viewModel: VisualizerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    abstract fun cameraViewModel(viewModel: CameraViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VisualizerMapViewModel::class)
    abstract fun visualizerMapViewModel(viewModel: VisualizerMapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    abstract fun favouritesViewModel(viewModel: FavouritesViewModel): ViewModel
}