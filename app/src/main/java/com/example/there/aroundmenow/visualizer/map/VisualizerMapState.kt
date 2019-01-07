package com.example.there.aroundmenow.visualizer.map

import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.google.android.gms.maps.model.Marker

data class VisualizerMapState(val selectedMarker: ViewDataState<Marker, Nothing>) {
    companion object {
        val INITIAL = VisualizerMapState(ViewDataState.Idle)
    }
}