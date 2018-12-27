package com.example.there.aroundmenow.visualizer.camera

import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
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
}