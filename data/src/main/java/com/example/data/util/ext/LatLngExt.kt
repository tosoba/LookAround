package com.example.data.util.ext

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil


fun LatLng.toBoundsWithRadius(radius: Double): LatLngBounds {
    val distanceFromCenterToCorner = radius * Math.sqrt(2.0)
    val southwestCorner = SphericalUtil.computeOffset(this, distanceFromCenterToCorner, 225.0)
    val northeastCorner = SphericalUtil.computeOffset(this, distanceFromCenterToCorner, 45.0)
    return LatLngBounds(southwestCorner, northeastCorner)
}

val LatLng.location: Location
    get() = Location("").also {
        it.latitude = latitude
        it.longitude = longitude
    }