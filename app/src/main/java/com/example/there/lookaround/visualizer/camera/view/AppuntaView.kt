package com.example.there.lookaround.visualizer.camera.view

import android.location.Location
import com.example.there.appuntalib.point.Point

interface AppuntaView {
    val points: ArrayList<Point>

    fun addPoint(p: Point) = points.add(p)

    fun addPoints(vararg newPoints: Point) = points.addAll(newPoints)

    fun removePoint(p: Point) = points.remove(p)

    interface Controller {
        val radarView: CameraRadarView?
        val cameraView: CameraView?

        fun updateRange(range: Double) {
            cameraView?.maxDistance = range
            radarView?.maxDistance = range
        }

        fun updateLocation(location: Location) {
            cameraView?.setPosition(location)
            radarView?.setPosition(location)
        }

        fun updatePoints(points: List<Point>) {
            cameraView?.setPoints(points)
        }

        fun updateRadarPoints(points: List<Point>) {
            radarView?.setPoints(points)
        }
    }
}