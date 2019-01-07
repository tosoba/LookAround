package com.example.there.aroundmenow.visualizer.map

import com.androidmapsextensions.Marker
import com.example.there.aroundmenow.base.architecture.view.ViewDataState

data class VisualizerMapState(val selectedMarker: ViewDataState<Marker, Nothing>) {
    companion object {
        val INITIAL = VisualizerMapState(ViewDataState.Idle)
    }
}