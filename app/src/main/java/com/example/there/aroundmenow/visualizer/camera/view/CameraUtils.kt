package com.example.there.aroundmenow.visualizer.camera.view

import com.example.there.appuntalib.point.renderer.impl.SimplePointRenderer

object CameraUtils {

    val radarRenderer: SimplePointRenderer by lazy { SimplePointRenderer() }

    var objectId = 0
        get() = field++
        private set

    fun resetObjectIdGenerator() {
        objectId = 0
    }

    const val defaultCameraRange = 500
}