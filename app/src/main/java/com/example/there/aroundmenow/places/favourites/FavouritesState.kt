package com.example.there.aroundmenow.places.favourites

import com.example.domain.task.error.GetFavouritePlacesError
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListEvent
import com.example.there.aroundmenow.model.UIPlace

data class FavouritesState(
    val places: ViewDataState<List<UIPlace>, GetFavouritePlacesError>,
    val listEvent: ViewDataState<SimplePlacesListEvent, Nothing>
) {
    companion object {
        val INITIAL = FavouritesState(ViewDataState.Idle, ViewDataState.Idle)
    }
}