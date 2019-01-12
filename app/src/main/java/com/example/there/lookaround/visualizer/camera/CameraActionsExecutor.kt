package com.example.there.lookaround.visualizer.camera

import com.example.there.appuntalib.point.Point
import com.example.there.lookaround.base.architecture.executor.RxActionsExecutor
import com.example.there.lookaround.base.architecture.view.ViewDataState
import javax.inject.Inject

class CameraActionsExecutor @Inject constructor(
    viewModel: CameraViewModel
) : RxActionsExecutor.HostUnaware<CameraState, CameraViewModel>(viewModel), CameraActions {

    override fun pageUp() = mutateState {
        it.copy(page = it.page + 1)
    }

    override fun pageDown() {
        if (lastState.page > 0) mutateState {
            it.copy(page = it.page - 1)
        }
    }

    override fun rangeUp() {
        if (lastState.rangeIndex < CameraState.Constants.cameraRanges.size - 1) mutateState {
            it.copy(rangeIndex = it.rangeIndex + 1)
        }
    }

    override fun rangeDown() {
        if (lastState.rangeIndex > 0) mutateState {
            it.copy(rangeIndex = it.rangeIndex - 1)
        }
    }

    override fun pointPressed(point: Point) = mutateState {
        it.copy(lastPressedPoint = ViewDataState.Value(point))
    }

    override fun cameraObjectDialogDismissed() = mutateState {
        it.copy(lastPressedPoint = ViewDataState.Idle)
    }
}