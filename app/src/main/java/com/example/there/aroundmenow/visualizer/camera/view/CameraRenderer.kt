package com.example.there.aroundmenow.visualizer.camera.view

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
import com.example.there.aroundmenow.util.ext.firstOrNull
import com.example.there.aroundmenow.util.ext.location
import com.google.android.gms.maps.model.LatLng

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

    private val grid: CameraGrid by lazy {
        CameraGrid(
            cameraParams.cameraGridNumberOfRows,
            cameraParams.cameraTopEdgePositionPx
        )
    }

    class CameraGrid(gridRows: Int, private val cameraTopEdgePositionPx: Int) {
        private val gridMap: MutableMap<Int, MutableMap<Pair<Int, Int>, CameraObject?>> = HashMap()

        private val columnNumbers = IntArray(GRID_NUMBER_OF_COLUMNS) { it }
        private val rowNumbers = IntArray(gridRows) { it }

        private val bearingRanges: List<IntRange> = columnNumbers.map {
            (it * BEARING_SPACING)..((it + 1) * BEARING_SPACING)
        }

        fun calculateCameraObjectPosition(cameraObject: CameraObject) {
            val column = calculateCameraObjectColumn(cameraObject.userLatLngBearing!!)
            if (gridMap.isEmpty()) {
                insertEmptyPage(0)
                gridMap[0]!![Pair(0, column)] = cameraObject
                cameraObject.pageNumber = 0
                cameraObject.yAxisPosition = cameraTopEdgePositionPx.toFloat()
            } else {
                var objectPosition: CameraObjectPosition? = null
                gridMap.keys.forEach outer@{ pageNumber ->
                    val page = gridMap[pageNumber]!!
                    rowNumbers.forEach { rowNumber ->
                        val cell = page[Pair(rowNumber, column)]
                        if (cell == null) {
                            val y = cameraTopEdgePositionPx + rowNumber * CameraObjectDialogConstants.HEIGHT
                            objectPosition = CameraObjectPosition(pageNumber, y)
                            return@outer
                        }
                    }
                }
                if (objectPosition == null) {
                    val nextPageNumber = gridMap.keys.max()!! + 1
                    insertEmptyPage(nextPageNumber)
                    gridMap[nextPageNumber]!![Pair(0, column)] = cameraObject
                    cameraObject.pageNumber = nextPageNumber
                    cameraObject.yAxisPosition = cameraTopEdgePositionPx.toFloat()
                } else {
                    cameraObject.pageNumber = objectPosition!!.pageNumber
                    //TODO: use this and remove yAxisPositionOnPage
                    cameraObject.yAxisPosition = objectPosition!!.yPosition
                }
            }
        }


        private fun insertEmptyPage(number: Int) {
            val pageKeys = rowNumbers.map { row ->
                columnNumbers.map { column ->
                    Pair(row, column)
                }
            }.flatten()

            val emptyPage = HashMap<Pair<Int, Int>, CameraObject?>().apply {
                putAll(pageKeys.map { it to null })
            }

            gridMap[number] = emptyPage
        }

        private fun calculateCameraObjectColumn(
            bearing: Double
        ): Int = bearingRanges.indexOfFirst { bearing in it }

        companion object {
            private const val BEARING_SPACING = 45
            private const val GRID_NUMBER_OF_COLUMNS = 360 / 45
        }
    }

    data class CameraObjectPosition(val pageNumber: Int, val yPosition: Float)

    // TODO: try to divide the screen into a grid - first calculate the number of rows based on screen height, toolbar height, dialog height etc. (probably in Application class) - round it down and store it in settings
    // there will be for example 8 columns (360 / 45) on each page (or a different number depending on what works :p)
    // assign cameraObject to a column based on its userLatLngBearing
    // look for an empty cell in that column starting with page 0
    // assign cameraObject to cell
    // store the information which "cells" are taken in a map or smth

    // this will HOPEFULLY help to avoid running loops for every cameraObject (like in getTakenYAxisPositionsForCameraObject)
    override fun drawPoint(point: Point, canvas: Canvas, orientation: Orientation) {
        val camObject = cameraObjects.findByPoint(point)
        val lastUserLatLng = userLatLng

        if (camObject != null && lastUserLatLng != null) {
            if (camObject.yAxisPosition == null) {
                camObject.userLatLngBearing = PointsUtil.calculateBearing(
                    lastUserLatLng.location,
                    camObject.place.latLng.location
                )
                grid.calculateCameraObjectPosition(camObject)
            }
            point.y = camObject.yAxisPosition!!

            if (currentPage == camObject.pageNumber) {
                val background = drawBackground(canvas, point)
                val textWidth = (background.width() - 10).toInt() // 10 to keep some space on the right for the "..."

                drawTitle(camObject.place.name, textWidth, canvas, point)

                val distance = lastUserLatLng.location.distanceTo(camObject.place.latLng.location)
                val distanceStr = String.format("%.1f m away", distance)
                drawDistance(distanceStr, textWidth, canvas, point)
            }
        }
    }

    private fun drawTitle(title: String, textWidth: Int, canvas: Canvas, point: Point) {
        val txtTitle = TextUtils.ellipsize(title, textPaint, textWidth.toFloat(), TextUtils.TruncateAt.END)
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
        if (cameraObject.userLatLngBearing == null) cameraObject.userLatLngBearing = PointsUtil.calculateBearing(
            latLng.location,
            cameraObject.place.latLng.location
        )

        return filter {
            it.point.name != cameraObject.point.name && it.yAxisPosition != null && it.userLatLngBearing != null
                    && Math.abs(it.userLatLngBearing!! - cameraObject.userLatLngBearing!!) < 45.0
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