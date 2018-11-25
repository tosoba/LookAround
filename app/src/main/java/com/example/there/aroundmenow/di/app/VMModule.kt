package com.example.there.aroundmenow.di.app

import androidx.lifecycle.ViewModel
import com.example.there.aroundmenow.di.vm.ViewModelKey
import com.example.there.aroundmenow.main.MainViewModel
import com.example.there.aroundmenow.places.pois.POIsViewModel
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
    @ViewModelKey(POIsViewModel::class)
    abstract fun poisViewModel(viewModel: POIsViewModel): ViewModel
}