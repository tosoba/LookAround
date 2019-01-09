package com.example.there.aroundmenow.util.lifecycle

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.there.aroundmenow.util.ext.latLng
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.patloew.rxlocation.RxLocation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.TimeUnit

class RxLocationComponent(
    private val activity: Activity,
    private val onGooglePlayServicesUnavailable: () -> Unit,
    private val onLocationChange: (LatLng) -> Unit,
    private val onLocationDisabled: () -> Unit
) : LifecycleObserver {

    private val disposables = CompositeDisposable()

    private val rxLocation: RxLocation by lazy {
        RxLocation(activity).apply { setDefaultTimeout(15, TimeUnit.SECONDS) }
    }

    private val locationRequest: LocationRequest by lazy {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10000)
    }

    private var updatesStarted: Boolean = false

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() = disposables.clear()

    fun tryStartUpdates() {
        if (!updatesStarted) {
            val apiAvailability = GoogleApiAvailability.getInstance()
            val status = apiAvailability.isGooglePlayServicesAvailable(activity)

            if (status != ConnectionResult.SUCCESS) {
                if (apiAvailability.isUserResolvableError(status))
                    apiAvailability.getErrorDialog(activity, status, 1).show()
                else onGooglePlayServicesUnavailable()
            } else startUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startUpdates() {
        disposables += rxLocation.settings().checkAndHandleResolution(locationRequest).subscribe { success ->
            if (success) {
                disposables += rxLocation.location().updates(locationRequest).subscribe({
                    onLocationChange(it.latLng)
                }, {
                    updatesStarted = false
                })
                updatesStarted = true
            } else onLocationDisabled()
        }
    }
}