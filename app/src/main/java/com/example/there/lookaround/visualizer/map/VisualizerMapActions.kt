package com.example.there.lookaround.visualizer.map

import com.androidmapsextensions.Marker


interface VisualizerMapActions {
    fun markerSelected(marker: Marker)
    fun markerInfoWindowDismissed()
}