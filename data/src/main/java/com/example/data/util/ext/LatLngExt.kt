package com.example.data.util.ext

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import kotlin.math.sqrt

val LatLng.location: Location
    get() = Location("").also {
        it.latitude = latitude
        it.longitude = longitude
    }

val LatLng.reverseGeocodingString: String
    get() = "$latitude,$longitude"

fun LatLng.toOverpassPOIsQueryWithRadius(
    radius: Int
): String = "[out:json];node[tourism=attraction]${toBoundsWithRadius(radius.toDouble()).overpassString};out%20meta;"

fun LatLng.toOverpassQueryWithRadius(
    query: String,
    radius: Int
): String = "[out:json];node$query${toBoundsWithRadius(radius.toDouble()).overpassString};out%20meta;"

fun LatLng.toBoundsWithRadius(radius: Double): LatLngBounds {
    val distanceFromCenterToCorner = radius * sqrt(2.0)
    val southwestCorner = SphericalUtil.computeOffset(this, distanceFromCenterToCorner, 225.0)
    val northeastCorner = SphericalUtil.computeOffset(this, distanceFromCenterToCorner, 45.0)
    return LatLngBounds(southwestCorner, northeastCorner)
}

private val LatLngBounds.overpassString: String
    get() = "(${southwest.latitude},${southwest.longitude},${northeast.latitude},${northeast.longitude})"

fun LatLng.formattedDistanceTo(
    other: LatLng
): String = "${String.format("%.2f", location.distanceTo(other.location))} m away"