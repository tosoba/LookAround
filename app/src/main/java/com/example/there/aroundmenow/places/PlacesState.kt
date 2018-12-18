package com.example.there.aroundmenow.places

import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.example.there.aroundmenow.base.architecture.view.ViewData

data class PlacesState(
    val lastGeocodingResult: ViewData<GeocodingInfo, ReverseGeocodeLocationError>
) {
    companion object {
        val INITIAL = PlacesState(ViewData.Idle)
    }
}