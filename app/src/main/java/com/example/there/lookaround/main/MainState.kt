package com.example.there.lookaround.main

import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.google.android.gms.maps.model.LatLng

data class MainState(
    val userLatLng: ViewDataState<LatLng, LocationUnavailableError>,
    val connectedToInternet: ViewDataState<Boolean, Nothing>
) {
    companion object {
        val INITIAL = MainState(ViewDataState.Idle, ViewDataState.Idle)
    }
}