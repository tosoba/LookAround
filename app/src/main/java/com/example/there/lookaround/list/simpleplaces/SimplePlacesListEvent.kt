package com.example.there.lookaround.list.simpleplaces

import com.example.there.lookaround.model.UISimplePlace

sealed class SimplePlacesListEvent(val place: UISimplePlace) {
    class VisualizationRequest(place: UISimplePlace) : SimplePlacesListEvent(place)
    class DetailsRequest(place: UISimplePlace) : SimplePlacesListEvent(place)
}