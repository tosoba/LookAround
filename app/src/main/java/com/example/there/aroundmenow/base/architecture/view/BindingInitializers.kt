package com.example.there.aroundmenow.base.architecture.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

interface BindingInitializer<Binding : ViewDataBinding> {
    fun Binding.init() = Unit
}

interface FragmentBindingInitializer<Binding : ViewDataBinding> :
    BindingInitializer<Binding>,
    LifecycleOwner {

    fun initializeView(
        @LayoutRes layoutResource: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = false
    ): View = DataBindingUtil.inflate<Binding>(
        inflater, layoutResource, container, attachToParent
    ).apply {
        setLifecycleOwner(this@FragmentBindingInitializer)
        init()
    }.root
}