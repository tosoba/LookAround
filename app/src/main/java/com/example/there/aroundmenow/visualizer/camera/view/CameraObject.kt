package com.example.there.aroundmenow.visualizer.camera.view

import com.example.there.appuntalib.point.impl.SimplePoint
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.util.ext.location

class CameraObject(
    val place: UISimplePlace,
    renderer: CameraRenderer,
    private val cameraParams: CameraParams
) {
    val point: SimplePoint = SimplePoint(CameraUtils.objectId, place.latLng.location, renderer)
    val radarPoint: SimplePoint = SimplePoint(CameraUtils.objectId, place.latLng.location, CameraUtils.radarRenderer)

    var yAxisPosition: Float? = null
        set(value) {
            if (value == null) return
            else {
                field = value
                yAxisPositionOnPage = value
                var y = value
                while (y + CameraObjectDialogConstants.HEIGHT / 2 > cameraParams.cameraBottomEdgePositionPx) {
                    y -= (cameraParams.screenHeightPx - cameraParams.cameraTopEdgePositionPx)
                    ++pageNumber
                }
            }
        }

    var yAxisPositionOnPage: Float? = null
        private set
        get() = if (field == null) null
        else field!! - pageNumber * (cameraParams.screenHeightPx - cameraParams.cameraTopEdgePositionPx)

    var pageNumber = 0
        private set
}