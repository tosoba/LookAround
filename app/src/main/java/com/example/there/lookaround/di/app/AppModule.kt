package com.example.there.lookaround.di.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.there.lookaround.di.vm.ViewModelFactory
import com.patloew.rxlocation.RxLocation
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun rxLocation(context: Context): RxLocation = RxLocation(context).apply {
            setDefaultTimeout(15, TimeUnit.SECONDS)
        }
    }
}