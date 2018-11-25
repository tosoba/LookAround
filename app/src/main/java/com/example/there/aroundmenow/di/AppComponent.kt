package com.example.there.aroundmenow.di

import android.app.Application
import com.example.there.aroundmenow.AroundMeNowApp
import com.example.there.aroundmenow.di.activity.ActivityBindingModule
import com.example.there.aroundmenow.di.app.AppModule
import com.example.there.aroundmenow.di.app.DataModule
import com.example.there.aroundmenow.di.app.NetworkModule
import com.example.there.aroundmenow.di.app.VMModule
import com.example.there.aroundmenow.di.fragment.FragmentBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class,
        NetworkModule::class,
        VMModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class
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