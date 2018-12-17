package com.example.there.aroundmenow.util.ext

import android.location.Location
import com.google.android.gms.maps.model.LatLng

val defaultLocation: Location
    get() = locationFromLatLng(0.0, 0.0)

val LatLng.location: Location
    get() = locationFromLatLng(latitude, longitude)

fun LatLng.distanceTo(other: LatLng): Float = location.distanceTo(other.location)

fun locationFromLatLng(lat: Double, lng: Double) = Location("").also {
    it.latitude = lat
    it.longitude = lng
}