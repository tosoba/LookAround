package com.example.there.aroundmenow.visualizer.map

import com.androidmapsextensions.Marker


interface VisualizerMapActions {
    fun markerSelected(marker: Marker)
    fun markerInfoWindowDismissed()
}