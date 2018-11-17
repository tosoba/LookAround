package com.example.there.aroundmenow.di

import android.app.Application
import com.example.there.aroundmenow.AroundMeNowApp
import com.example.there.aroundmenow.di.module.AppModule
import com.example.there.aroundmenow.di.module.DataModule
import com.example.there.aroundmenow.di.module.NetworkModule
import com.example.there.aroundmenow.di.module.ViewModelModule
import com.example.there.aroundmenow.di.module.view.MainActivityModule
import com.example.there.aroundmenow.di.module.view.SearchFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class,
        NetworkModule::class,
        MainActivityModule::class,
        SearchFragmentModule::class,
        ViewModelModule::class
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