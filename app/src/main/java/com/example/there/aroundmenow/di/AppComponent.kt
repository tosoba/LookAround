package com.example.there.aroundmenow.di

import android.app.Application
import com.example.there.aroundmenow.AroundMeNowApp
import com.example.there.aroundmenow.di.module.AppModule
import com.example.there.aroundmenow.di.module.DataModule
import com.example.there.aroundmenow.di.module.NetworkModule
import com.example.there.aroundmenow.di.module.PlacesActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        NetworkModule::class,
        PlacesActivityModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AroundMeNowApp)
}