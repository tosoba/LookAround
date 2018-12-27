package com.example.there.aroundmenow.visualizer.camera

data class CameraState(
    val page: Int,
    val rangeIndex: Int
) {
    companion object {
        val INITIAL = CameraState(0, 2)
    }

    object Constants {
        val cameraRanges = sortedMapOf(
            100 to "100 m",
            200 to "250 m",
            500 to "500 m",
            1000 to "1 km",
            2000 to "2 km"
        )
    }
}