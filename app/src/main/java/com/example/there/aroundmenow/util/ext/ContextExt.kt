package com.example.there.aroundmenow.util.ext

import android.content.Context
import android.util.DisplayMetrics
import android.view.Surface
import android.view.WindowManager

val Context.screenDimensionsPx: Pair<Int, Int>
    get() {
        val displayMetrics = resources.displayMetrics
        return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }

fun Context.dpToPx(
    dp: Float
): Float = dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

enum class ScreenOrientation { HORIZONTAL, VERTICAL }

val Context.orientation: ScreenOrientation
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val screenRotation = wm.defaultDisplay.rotation
        return if (screenRotation == Surface.ROTATION_90 || screenRotation == Surface.ROTATION_270)
            ScreenOrientation.HORIZONTAL else ScreenOrientation.VERTICAL
    }