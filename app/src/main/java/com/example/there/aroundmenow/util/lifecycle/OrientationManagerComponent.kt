package com.example.there.aroundmenow.util.lifecycle

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.there.appuntalib.orientation.OrientationManager

class OrientationManagerComponent(
    private val orientationManager: OrientationManager,
    private val activity: Activity
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() = orientationManager.startSensor(activity)

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() = orientationManager.stopSensor()
}