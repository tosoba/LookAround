package com.example.there.lookaround.main

import com.google.android.gms.maps.model.LatLng

interface MainActions {
    fun setConnectedToInternet(connected: Boolean)
    fun setUserLatLng(latLng: LatLng)
}