package com.example.there.aroundmenow.util.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

operator fun Lifecycle.plusAssign(observer: LifecycleObserver) = addObserver(observer)