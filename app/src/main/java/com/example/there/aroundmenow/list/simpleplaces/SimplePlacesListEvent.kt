package com.example.there.aroundmenow.list.simpleplaces

import com.example.there.aroundmenow.model.UISimplePlace

sealed class SimplePlacesListEvent(val place: UISimplePlace) {
    class VisualizationRequest(place: UISimplePlace) : SimplePlacesListEvent(place)
    class DetailsRequest(place: UISimplePlace) : SimplePlacesListEvent(place)
}