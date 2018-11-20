package com.example.there.aroundmenow.di.module.view

import androidx.lifecycle.ViewModel
import com.example.there.aroundmenow.di.vm.ViewModelKey
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.places.pois.POIsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class POIsFragmentModule {

    @ContributesAndroidInjector
    abstract fun poisFragment(): POIsFragment

    @Binds
    @IntoMap
    @ViewModelKey(POIsViewModel::class)
    abstract fun poisViewModel(viewModel: POIsViewModel): ViewModel
}