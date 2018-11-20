package com.example.there.aroundmenow.main

import android.location.Location
import com.example.there.aroundmenow.util.ext.defaultLocation

data class MainState(
    val userLocation: Location
) {
    companion object {
        val INITIAL = MainState(defaultLocation)
    }
}