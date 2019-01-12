package com.example.there.lookaround.di

import android.app.Application
import com.example.there.lookaround.LookAroundApp
import com.example.there.lookaround.di.activity.ActivityBindingModule
import com.example.there.lookaround.di.app.AppModule
import com.example.there.lookaround.di.app.DataModule
import com.example.there.lookaround.di.app.NetworkModule
import com.example.there.lookaround.di.app.VMModule
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
        ActivityBindingModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: LookAroundApp)
}