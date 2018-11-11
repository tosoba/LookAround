package com.example.data.db.model

import android.location.Location

class SavedPOIsLocation(val latitude: Double, val longitude: Double) {
    val location: Location
        get() = Location("").also {
            it.latitude = latitude
            it.longitude = longitude
        }
}