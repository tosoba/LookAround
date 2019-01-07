package com.example.there.aroundmenow.util.view.map

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.androidmapsextensions.Marker


abstract class OnInfoWindowElementTouchListener(
    private val view: View,
    private val bgDrawableNormal: Drawable,
    private val bgDrawablePressed: Drawable
) : OnTouchListener {

    private val handler = Handler()

    var marker: Marker? = null
    private var pressed = false

    private val confirmClickRunnable = Runnable {
        if (endPress() && marker != null) {
            onClickConfirmed(view, marker!!)
        }
    }

    override fun onTouch(vv: View, event: MotionEvent): Boolean {
        if (0 <= event.x && event.x <= view.width && 0 <= event.y && event.y <= view.height) {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> startPress()

                // We need to delay releasing of the view a little so it shows the pressed state on the screen
                MotionEvent.ACTION_UP -> handler.postDelayed(confirmClickRunnable, 150)

                MotionEvent.ACTION_CANCEL -> endPress()
            }
        } else {
            // If the touch goes outside of the view's area
            // (like when moving finger out of the pressed button)
            // just release the press
            endPress()
        }
        return false
    }

    private fun startPress() {
        if (!pressed) {
            pressed = true
            handler.removeCallbacks(confirmClickRunnable)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.background = bgDrawablePressed
            }
            marker?.showInfoWindow()
        }
    }

    private fun endPress(): Boolean = if (pressed) {
        this.pressed = false
        handler.removeCallbacks(confirmClickRunnable)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = bgDrawableNormal
        }
        marker?.showInfoWindow()

        true
    } else false

    /**
     * This is called after a successful click
     */
    protected abstract fun onClickConfirmed(v: View, marker: Marker)
}