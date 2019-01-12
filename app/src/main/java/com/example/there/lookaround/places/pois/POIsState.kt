package com.example.there.lookaround.places.pois

import com.example.domain.task.error.FindNearbyPlacesError
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.model.UISimplePlace


data class POIsState(
    val pois: ViewDataState<List<UISimplePlace>, FindNearbyPlacesError>
) {
    companion object {
        val INITIAL = POIsState(ViewDataState.Idle)
    }
}