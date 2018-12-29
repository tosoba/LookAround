package com.example.there.aroundmenow.util.ext

import android.content.Context
import android.util.DisplayMetrics

val Context.screenDimensionsPx: Pair<Int, Int>
    get() {
        val displayMetrics = resources.displayMetrics
        return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }

fun Context.dpToPx(
    dp: Float
): Float = dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

val Context.orientation: Int
    get() = resources.configuration.orientation