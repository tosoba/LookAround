package com.example.there.aroundmenow.placedetails

import android.graphics.Bitmap
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.error.FindPlacePhotosError
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.google.android.gms.location.places.Place

data class PlaceDetailsState(
    val place: ViewDataState<Place, FindPlaceDetailsError>,
    val photos: ViewDataState<List<Bitmap>, FindPlacePhotosError>
) {
    companion object {
        val INITIAL = PlaceDetailsState(ViewDataState.Idle, ViewDataState.Idle)
    }
}