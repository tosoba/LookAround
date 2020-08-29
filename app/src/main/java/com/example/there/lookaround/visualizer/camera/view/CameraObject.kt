package com.example.there.lookaround.visualizer.camera.view

import com.example.there.appuntalib.point.impl.SimplePoint
import com.example.there.lookaround.model.UISimplePlace
import com.example.there.lookaround.util.ext.location

class CameraObject(
    val place: UISimplePlace,
    renderer: CameraRenderer,
    private val cameraParams: CameraParams
) {
    val point: SimplePoint = SimplePoint(CameraUtils.objectId, place.latLng.location, renderer)
        .apply { name = place.name }
    val radarPoint: SimplePoint = SimplePoint(CameraUtils.objectId, place.latLng.location, CameraUtils.radarRenderer)

    var yAxisPosition: Float? = null
        set(value) {
            if (value == null) return
            else {
                field = value
                yAxisPositionOnPage = value
            }
        }

    var yAxisPositionOnPage: Float? = null
        private set(value) {
            field =
                value!! - pageNumber * (cameraParams.screenHeightPx - cameraParams.cameraTopEdgePositionPx)
        }

    var pageNumber = 0

    var userLatLngBearing: Double? = null
}