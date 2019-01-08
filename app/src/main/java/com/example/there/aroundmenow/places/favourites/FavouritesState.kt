package com.example.there.aroundmenow.places.favourites

import com.example.domain.repo.model.SavedPlace
import com.example.domain.task.error.GetFavouritePlacesError
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListEvent

data class FavouritesState(
    val places: ViewDataState<List<SavedPlace>, GetFavouritePlacesError>,
    val listEvent: ViewDataState<SimplePlacesListEvent, Nothing>
) {
    companion object {
        val INITIAL = FavouritesState(ViewDataState.Idle, ViewDataState.Idle)
    }
}