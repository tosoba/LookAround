package com.example.there.lookaround.util.lifecycle

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.there.appuntalib.orientation.OrientationManager

class OrientationManagerComponent(
    private val orientationManager: OrientationManager,
    private val activity: Activity
) : LifecycleObserver {

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() = orientationManager.startSensor(activity)

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() = orientationManager.stopSensor()
}