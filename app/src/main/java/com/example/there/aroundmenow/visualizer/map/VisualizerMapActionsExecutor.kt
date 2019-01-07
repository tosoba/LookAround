package com.example.there.aroundmenow.visualizer.map

import com.androidmapsextensions.Marker
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import javax.inject.Inject

class VisualizerMapActionsExecutor @Inject constructor(
    viewModel: VisualizerMapViewModel
) : RxActionsExecutor.HostUnaware<VisualizerMapState, VisualizerMapViewModel>(viewModel), VisualizerMapActions {

    override fun markerSelected(marker: Marker) = mutateState {
        it.copy(selectedMarker = ViewDataState.Value(marker))
    }

    override fun markerInfoWindowDismissed() = mutateState {
        it.copy(selectedMarker = ViewDataState.Idle)
    }
}