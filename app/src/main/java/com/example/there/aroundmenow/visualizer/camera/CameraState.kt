package com.example.there.aroundmenow.visualizer.camera

import com.example.there.appuntalib.point.Point
import com.example.there.aroundmenow.base.architecture.view.ViewDataState

data class CameraState(
    val page: Int,
    val rangeIndex: Int,
    val lastPressedPoint: ViewDataState<Point, Nothing>
) {
    companion object {
        val INITIAL = CameraState(0, 2, ViewDataState.Idle)
    }

    object Constants {
        val cameraRanges = sortedMapOf(
            50 to "50 m",
            100 to "100 m",
            200 to "250 m",
            500 to "500 m",
            1000 to "1 km"
        )
    }
}