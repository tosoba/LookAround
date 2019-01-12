package com.example.there.lookaround.visualizer

import com.example.domain.task.error.FindNearbyPlacesError
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.model.UISimplePlace

data class VisualizerState(
    val places: ViewDataState<List<UISimplePlace>, FindNearbyPlacesError>
) {
    companion object {
        val INITIAL = VisualizerState(ViewDataState.Idle)
    }
}