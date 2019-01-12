package com.example.there.lookaround.util.ext

import android.app.Activity
import android.content.pm.ActivityInfo
import android.provider.Settings
import android.view.Surface
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.there.lookaround.base.architecture.view.ViewObservingActivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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

fun ViewObservingActivity.observeInternetConnectivity(onNext: (Boolean) -> Unit) = ReactiveNetwork
    .observeInternetConnectivity()
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeWithAutoDispose(onNext)

val Activity.isLocationAvailable: Boolean
    get() {
        val locationProviders =
            Settings.Secure.getString(contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
        return !(locationProviders == null || locationProviders == "")
    }