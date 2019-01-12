package com.example.there.lookaround.places

import com.example.domain.repo.model.ReverseGeocodingInfo
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.example.there.lookaround.base.architecture.view.ViewDataState

data class PlacesState(
    val lastGeocodingResult: ViewDataState<ReverseGeocodingInfo, ReverseGeocodeLocationError>
) {
    companion object {
        val INITIAL = PlacesState(ViewDataState.Idle)
    }
}