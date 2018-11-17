package com.example.data.db.model

import android.location.Location
import com.google.android.gms.maps.model.LatLng

class SavedPOIsLocation(val latLng: LatLng) {
    val location: Location
        get() = Location("").also {
            it.latitude = latLng.latitude
            it.longitude = latLng.longitude
        }
}