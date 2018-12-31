package com.example.there.aroundmenow.util.ext

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.Surface
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


fun Activity.registerFragmentLifecycleCallbacks(
    callbacks: FragmentManager.FragmentLifecycleCallbacks,
    recursive: Boolean
) = (this as? FragmentActivity)
    ?.supportFragmentManager
    ?.registerFragmentLifecycleCallbacks(callbacks, recursive)

fun Activity.disableScreenRotation() {
    requestedOrientation = when (windowManager?.defaultDisplay?.rotation) {
        Surface.ROTATION_0, Surface.ROTATION_180 -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Surface.ROTATION_90, Surface.ROTATION_270 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        else -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}

fun Activity.enableScreenRotation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}