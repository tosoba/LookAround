package com.example.there.aroundmenow.places

import com.example.domain.task.error.ReverseGeocodeLocationError
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.model.UIGeocodingInfo
import com.google.android.gms.maps.model.LatLng

data class PlacesState(
    val userLatLng: ViewData<LatLng, Nothing>,
    val lastGeocodingResult: ViewData<UIGeocodingInfo, ReverseGeocodeLocationError>
) {
    companion object {
        val INITIAL = PlacesState(ViewData.Idle, ViewData.Idle)
    }
}