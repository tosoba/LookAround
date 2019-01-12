package com.example.there.lookaround.places.favourites

import com.example.there.lookaround.list.simpleplaces.SimplePlacesListEvent

interface FavouritesActions {
    fun getFavouritesPlaces()
    fun onListEvent(event: SimplePlacesListEvent)
    fun onListEventHandled()
}