package com.example.there.aroundmenow.placedetails

import com.example.domain.task.error.FindPlaceDetailsError
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import se.walkercrou.places.Place

data class PlaceDetailsState(
    val place: ViewDataState<Place, FindPlaceDetailsError>
) {
    companion object {
        val INITIAL = PlaceDetailsState(ViewDataState.Idle)
    }
}