package com.example.there.aroundmenow.places

import com.example.domain.task.error.ReverseGeocodeLocationError
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.model.UIGeocodingInfo

data class PlacesState(
    val lastGeocodingResult: ViewData<UIGeocodingInfo, ReverseGeocodeLocationError>
) {
    companion object {
        val INITIAL = PlacesState(ViewData.Idle)
    }
}