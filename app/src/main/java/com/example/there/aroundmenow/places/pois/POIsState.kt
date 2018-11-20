package com.example.there.aroundmenow.places.pois

import com.google.android.gms.location.places.Place

data class POIsState(
    val pois: List<Place>
) {
    companion object {
        val INITIAL = POIsState(emptyList())
    }
}