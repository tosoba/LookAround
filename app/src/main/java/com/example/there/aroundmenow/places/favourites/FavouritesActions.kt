package com.example.there.aroundmenow.places.favourites

import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListEvent

interface FavouritesActions {
    fun getFavouritesPlaces()
    fun onListEvent(event: SimplePlacesListEvent)
    fun onListEventHandled()
}