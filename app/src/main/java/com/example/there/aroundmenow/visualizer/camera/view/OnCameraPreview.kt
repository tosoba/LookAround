package com.example.there.aroundmenow.visualizer.camera.view

import com.example.there.appuntalib.ui.ICameraCallbacks

interface OnCameraPreview : ICameraCallbacks {
    override fun onCameraStart() = Unit
    override fun onCameraOpen() = Unit
}