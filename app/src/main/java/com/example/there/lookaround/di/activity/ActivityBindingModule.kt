package com.example.there.lookaround.di.activity

import com.example.there.lookaround.di.activity.main.MainModule
import com.example.there.lookaround.di.scope.ActivityScope
import com.example.there.lookaround.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity
}