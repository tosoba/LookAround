package com.example.there.aroundmenow.base.architecture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

sealed class LayoutInitializer {
    abstract class DataBindingLayoutInitializer<B> : LayoutInitializer() where B : ViewDataBinding {
        abstract fun initializeLayout(): B
        abstract val binding: B?
    }

    abstract class DefaultActivityLayoutInitializer : LayoutInitializer() {
        abstract fun initializeLayout()
    }

    abstract class DefaultFragmentLayoutInitializer : LayoutInitializer() {
        abstract fun initializeLayout(inflater: LayoutInflater, container: ViewGroup?): View?
    }
}