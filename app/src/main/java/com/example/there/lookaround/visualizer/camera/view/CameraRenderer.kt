package com.example.there.lookaround.visualizer.camera.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.text.TextUtils
import com.example.there.appuntalib.orientation.Orientation
import com.example.there.appuntalib.point.Point
import com.example.there.appuntalib.point.PointsUtil
import com.example.there.appuntalib.point.renderer.PointRenderer
import com.example.there.lookaround.util.ext.firstOrNull
import com.example.there.lookaround.util.ext.location
import com.google.android.gms.maps.model.LatLng
import kotlin.math.abs

class CameraRenderer(private val cameraParams: CameraParams) : PointRenderer {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#80000000")
    }

    private val textPaint = TextPaint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
        textAlign = Paint.Align.LEFT
        isLinearText = true
    }

    var cameraObjects: List<CameraObject> = emptyList()

    var currentPage = 0

    var userLatLng: LatLng? = null

    override fun drawPoint(point: Point, canvas: Canvas, orientation: Orientation) {
        val camObject = cameraObjects.findByPoint(point)
        val lastUserLatLng = userLatLng

        if (camObject != null && lastUserLatLng != null) {
            if (camObject.yAxisPosition == null) {
                val (yPosition, pageNumber) = findNextYAxisPosition(
                    cameraObjects.getTakenYAxisPositionsForCameraObject(camObject)
                )
                camObject.yAxisPosition = yPosition
                camObject.pageNumber = pageNumber
            }
            point.y = camObject.yAxisPositionOnPage!!

            if (currentPage == camObject.pageNumber) {
                val background = drawBackground(canvas, point)
                val textWidth =
                    (background.width() - 10).toInt() // 10 to keep some space on the right for the "..."

                drawTitle(camObject.place.name, textWidth, canvas, point)

                val distance = lastUserLatLng.location.distanceTo(camObject.place.latLng.location)
                val distanceStr = String.format("%.1f m away", distance)
                drawDistance(distanceStr, textWidth, canvas, point)
            }
        }
    }

    private fun drawTitle(title: String, textWidth: Int, canvas: Canvas, point: Point) {
        val txtTitle =
            TextUtils.ellipsize(title, textPaint, textWidth.toFloat(), TextUtils.TruncateAt.END)
        textPaint.textSize = CameraObjectDialogConstants.TITLE_TEXT_SIZE
        canvas.drawText(
            txtTitle,
            0,
            txtTitle.length,
            point.x - CameraObjectDialogConstants.WIDTH / 2 + CameraObjectDialogConstants.TEXT_VERTICAL_SPACING,
            point.y,
            textPaint
        )
    }

    private fun drawDistance(distance: String, textWidth: Int, canvas: Canvas, point: Point) {
        val txtDistance =
            TextUtils.ellipsize(distance, textPaint, textWidth.toFloat(), TextUtils.TruncateAt.END)
        textPaint.textSize = CameraObjectDialogConstants.DISTANCE_TEXT_SIZE
        canvas.drawText(
            txtDistance,
            0,
            txtDistance.length,
            point.x - CameraObjectDialogConstants.WIDTH / 2 + CameraObjectDialogConstants.TEXT_VERTICAL_SPACING,
            point.y + CameraObjectDialogConstants.TITLE_TEXT_SIZE,
            textPaint
        )
    }

    private fun List<CameraObject>.findByPoint(
        point: Point
    ): CameraObject? = firstOrNull { it.point.name == point.name }

    private fun List<CameraObject>.getTakenYAxisPositionsForCameraObject(
        cameraObject: CameraObject
    ): List<Float> = userLatLng?.let { latLng ->
        if (cameraObject.userLatLngBearing == null) {
            cameraObject.userLatLngBearing = PointsUtil.calculateBearing(
                latLng.location,
                cameraObject.place.latLng.location
            )
        }

        return filter {
            it.point.name != cameraObject.point.name && it.yAxisPosition != null && it.userLatLngBearing != null
                    && abs(it.userLatLngBearing!! - cameraObject.userLatLngBearing!!) < 45.0
        }.map { co -> co.yAxisPosition!! }
    } ?: emptyList()

    private fun findNextYAxisPosition(takenYAxisPositions: List<Float>): Pair<Float, Int> {
        var y = cameraParams.cameraTopEdgePositionPx.toFloat()
        var pageNumber = 0

        while (takenYAxisPositions.contains(y)) {
            y += CameraObjectDialogConstants.HEIGHT
            if (y > (pageNumber + 1) * cameraParams.cameraBottomEdgePositionPx) {
                pageNumber++
                y += cameraParams.cameraTopEdgePositionPx
            }
        }

        return Pair(y, pageNumber)
    }

    private fun drawBackground(canvas: Canvas, point: Point): RectF = RectF(
        point.x - CameraObjectDialogConstants.WIDTH / 2,
        point.y - CameraObjectDialogConstants.HEIGHT / 2,
        point.x + CameraObjectDialogConstants.WIDTH / 2,
        point.y + CameraObjectDialogConstants.HEIGHT / 2
    ).apply {
        canvas.drawRoundRect(this, 10f, 10f, backgroundPaint)
    }
}