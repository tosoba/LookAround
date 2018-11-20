package com.example.there.aroundmenow.util.ext

import android.location.Location

val defaultLocation: Location
    get() = Location("").apply {
        latitude = 0.0
        longitude = 0.0
    }