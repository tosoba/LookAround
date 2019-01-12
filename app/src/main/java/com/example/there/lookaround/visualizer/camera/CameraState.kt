package com.example.there.lookaround.visualizer.camera

import com.example.there.appuntalib.point.Point
import com.example.there.lookaround.base.architecture.view.ViewDataState

data class CameraState(
    val page: Int,
    val rangeIndex: Int,
    val lastPressedPoint: ViewDataState<Point, Nothing>
) {
    companion object {
        val INITIAL = CameraState(0, 3, ViewDataState.Idle)
    }

    object Constants {
        val cameraRanges = sortedMapOf(
            50 to "50 m",
            100 to "100 m",
            250 to "250 m",
            500 to "500 m",
            1000 to "1 km",
            2000 to "2 km",
            5000 to "5 km"
        )
    }
}