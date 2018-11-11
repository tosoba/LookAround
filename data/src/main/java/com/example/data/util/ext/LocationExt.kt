package com.example.data.util.ext

import android.location.Location

fun Location.isWithinDistanceFrom(
    other: Location,
    distance: Int
): Boolean = distanceTo(other) < distance.toFloat()