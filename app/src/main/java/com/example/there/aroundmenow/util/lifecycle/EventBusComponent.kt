package com.example.there.aroundmenow.util.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.greenrobot.eventbus.EventBus

class EventBusComponent(private val owner: LifecycleOwner) : LifecycleObserver {

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() = EventBus.getDefault().register(owner)

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() = EventBus.getDefault().unregister(owner)
}