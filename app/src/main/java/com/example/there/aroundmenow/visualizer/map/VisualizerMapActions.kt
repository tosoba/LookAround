package com.example.there.aroundmenow.visualizer.map

import com.google.android.gms.maps.model.Marker

interface VisualizerMapActions {
    fun markerSelected(marker: Marker)
    fun markerInfoWindowDismissed()
}