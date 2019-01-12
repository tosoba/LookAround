package com.example.there.lookaround.places.favourites

import com.example.domain.task.error.GetFavouritePlacesError
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.list.simpleplaces.SimplePlacesListEvent
import com.example.there.lookaround.model.UIPlace

data class FavouritesState(
    val places: ViewDataState<List<UIPlace>, GetFavouritePlacesError>,
    val listEvent: ViewDataState<SimplePlacesListEvent, Nothing>
) {
    companion object {
        val INITIAL = FavouritesState(ViewDataState.Idle, ViewDataState.Idle)
    }
}