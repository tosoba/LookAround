package com.example.there.lookaround.util.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

operator fun Lifecycle.plusAssign(observer: LifecycleObserver) = addObserver(observer)