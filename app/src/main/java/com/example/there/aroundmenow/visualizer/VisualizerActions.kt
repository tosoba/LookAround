package com.example.there.aroundmenow.visualizer

import com.example.there.aroundmenow.model.UIPlaceType
import com.example.there.aroundmenow.model.UISimplePlace

interface VisualizerActions {
    fun findNearbyPlacesOfType(placeType: UIPlaceType)
    fun setPlaces(places: List<UISimplePlace>)
    fun onNoInternetConnection()
    fun onUserLatLngUnavailable()
}