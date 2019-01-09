package com.example.there.aroundmenow.placedetails

import android.graphics.Bitmap
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.error.FindPlacePhotosError
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.model.UIPlace

data class PlaceDetailsState(
    val place: ViewDataState<UIPlace, FindPlaceDetailsError>,
    val photos: ViewDataState<List<Bitmap>, FindPlacePhotosError>
) {
    companion object {
        val INITIAL = PlaceDetailsState(ViewDataState.Idle, ViewDataState.Idle)
    }
}