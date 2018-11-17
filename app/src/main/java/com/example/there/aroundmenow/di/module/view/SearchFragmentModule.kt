package com.example.there.aroundmenow.di.module.view

import com.example.there.aroundmenow.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchFragmentModule {
    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment
}