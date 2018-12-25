package com.example.there.aroundmenow.visualizer

import com.example.domain.task.error.FindNearbyPlacesError
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.model.UISimplePlace

data class VisualizerState(
    val places: ViewDataState<List<UISimplePlace>, FindNearbyPlacesError>
) {
    companion object {
        val INITIAL = VisualizerState(ViewDataState.Idle)
    }
}