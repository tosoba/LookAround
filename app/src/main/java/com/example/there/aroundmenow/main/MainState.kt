package com.example.there.aroundmenow.main

import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.google.android.gms.maps.model.LatLng

data class MainState(
    val userLatLng: ViewData<LatLng, Nothing>
) {
    companion object {
        val INITIAL = MainState(ViewData.Value(LatLng(51.50354, -0.12768)))
    }
}