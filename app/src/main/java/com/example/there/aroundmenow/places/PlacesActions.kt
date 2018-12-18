package com.example.there.aroundmenow.places

import com.google.android.gms.maps.model.LatLng

interface PlacesActions {
    fun reverseGeocodeLocation(latLng: LatLng)
}