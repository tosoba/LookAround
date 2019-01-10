package com.example.there.aroundmenow.util.ext

import android.location.Address
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

val Location.latLng: LatLng
    get() = LatLng(latitude, longitude)

val Address.latLng: LatLng?
    get() = if (hasLatitude() && hasLongitude()) LatLng(latitude, longitude) else null

val Address.wholeAddressString: String
    get() = if (maxAddressLineIndex == -1) ""
    else (0..maxAddressLineIndex).joinToString(" ") { getAddressLine(it) }
