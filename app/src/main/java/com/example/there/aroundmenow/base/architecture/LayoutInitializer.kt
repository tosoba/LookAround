package com.example.there.aroundmenow.base.architecture

import androidx.databinding.ViewDataBinding

sealed class LayoutInitializer {
    abstract class DataBindingLayoutInitializer<B>: LayoutInitializer() where B : ViewDataBinding {
        abstract val binding: B
    }

    abstract class DefaultLayoutInitializer: LayoutInitializer() {
        abstract fun initializeLayout()
    }
}