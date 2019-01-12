package com.example.there.lookaround.visualizer

import com.example.there.lookaround.model.UIPlaceType
import com.example.there.lookaround.model.UISimplePlace

interface VisualizerActions {
    fun findNearbyPlacesOfType(placeType: UIPlaceType)
    fun setPlaces(places: List<UISimplePlace>)
    fun onNoInternetConnection()
    fun onUserLatLngUnavailable()
}