package com.example.there.aroundmenow.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}