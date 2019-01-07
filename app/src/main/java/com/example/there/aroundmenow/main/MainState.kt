package com.example.there.aroundmenow.main

import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.google.android.gms.maps.model.LatLng

data class MainState(
    val userLatLng: ViewDataState<LatLng, Nothing>,
    val connectedToInternet: ViewDataState<Boolean, Nothing>
) {
    companion object {
        val INITIAL = MainState(ViewDataState.Idle, ViewDataState.Idle)
    }
}