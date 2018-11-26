package com.example.there.aroundmenow.places.pois

import com.google.android.gms.maps.model.LatLng

interface POIsActions {
    fun findPOIsNearby(latLng: LatLng)
}