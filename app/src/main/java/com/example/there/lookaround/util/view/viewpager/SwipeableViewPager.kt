package com.example.there.lookaround.util.view.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.example.there.lookaround.R


class SwipeableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private val swipeable: Boolean

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SwipeableViewPager)
        try {
            swipeable = a.getBoolean(R.styleable.SwipeableViewPager_swipeable, true)
        } finally {
            a.recycle()
        }
    }

    override fun onInterceptTouchEvent(
        event: MotionEvent
    ): Boolean = if (swipeable) super.onInterceptTouchEvent(event) else false


    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean = if (swipeable) super.onTouchEvent(event) else false
}