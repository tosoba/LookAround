package com.example.there.lookaround.util.ext

import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar


fun Snackbar.showWithBottomMargin(marginPx: Int) {
    val params = view.layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin + marginPx)
    view.layoutParams = params
    show()
}