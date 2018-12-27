package com.example.there.aroundmenow.visualizer.camera.view

import android.content.Context
import android.util.AttributeSet
import com.example.there.appuntalib.point.Point
import com.example.there.appuntalib.ui.EyeView

class CameraView : EyeView, AppuntaView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    @Suppress("UNCHECKED_CAST")
    override val points: ArrayList<Point>
        get() = getpoints() as ArrayList<Point>
}