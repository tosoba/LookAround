package com.example.there.lookaround.places.pois

import com.google.android.gms.maps.model.LatLng

interface POIsActions {
    fun findPOIsNearby(latLng: LatLng)
}