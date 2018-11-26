package com.example.there.aroundmenow.places.placetypes

data class PlaceTypesState(
    val placeTypes: List<String>
) {
    companion object {
        val INITIAL = PlaceTypesState(emptyList())
    }
}