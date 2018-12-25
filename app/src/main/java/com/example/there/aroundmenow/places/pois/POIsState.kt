package com.example.there.aroundmenow.places.pois

import com.example.domain.task.error.FindNearbyPlacesError
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.model.UISimplePlace


data class POIsState(
    val pois: ViewDataState<List<UISimplePlace>, FindNearbyPlacesError>
) {
    companion object {
        val INITIAL = POIsState(ViewDataState.Idle)
    }
}