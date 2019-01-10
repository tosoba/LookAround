package com.example.there.aroundmenow.visualizer.camera.view

import com.example.there.appuntalib.point.impl.SimplePoint
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.util.ext.location

class CameraObject(
    val place: UISimplePlace,
    renderer: CameraRenderer
) {
    val point: SimplePoint =
        SimplePoint(CameraUtils.objectId, place.latLng.location, renderer).apply { name = place.name }
    val radarPoint: SimplePoint = SimplePoint(CameraUtils.objectId, place.latLng.location, CameraUtils.radarRenderer)

    var yAxisPosition: Float? = null

    var pageNumber = 0

    var userLatLngBearing: Double? = null
}