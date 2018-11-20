package com.example.there.aroundmenow.di.module.view

import androidx.lifecycle.ViewModel
import com.example.there.aroundmenow.di.vm.ViewModelKey
import com.example.there.aroundmenow.main.MainActivity
import com.example.there.aroundmenow.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel
}