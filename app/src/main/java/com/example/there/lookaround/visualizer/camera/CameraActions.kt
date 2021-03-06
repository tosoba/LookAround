package com.example.there.lookaround.visualizer.camera

import com.example.there.appuntalib.point.Point

interface CameraActions {
    fun pageUp()
    fun pageDown()
    fun rangeUp()
    fun rangeDown()

    fun pointPressed(point: Point)
    fun cameraObjectDialogDismissed()
}