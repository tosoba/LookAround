package com.example.there.lookaround.util.view.map

import android.content.res.Resources
import android.graphics.*
import android.graphics.Paint.Align
import android.util.LruCache
import com.androidmapsextensions.ClusterOptions
import com.androidmapsextensions.ClusterOptionsProvider
import com.androidmapsextensions.Marker
import com.example.there.lookaround.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class MapClusterOptionsProvider(resources: Resources) : ClusterOptionsProvider {

    private val baseBitmaps: ArrayList<Bitmap> = ArrayList()

    private val cache = LruCache<Int, BitmapDescriptor>(128)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Align.CENTER
        textSize = resources.getDimension(R.dimen.cluster_text_size)
    }

    private val bounds = Rect()

    private val clusterOptions = ClusterOptions().anchor(0.5f, 0.5f)

    init {
        markerDrawables.forEach { id ->
            baseBitmaps.add(BitmapFactory.decodeResource(resources, id))
        }
    }

    override fun getClusterOptions(markers: List<Marker>): ClusterOptions {
        val markersCount = markers.size
        val cachedIcon = cache.get(markersCount)
        if (cachedIcon != null) {
            return clusterOptions.icon(cachedIcon)
        }

        var base: Bitmap
        var i = 0
        do {
            base = baseBitmaps[i]
        } while (markersCount >= markerCounts[i++])

        val bitmap = base.copy(Bitmap.Config.ARGB_8888, true)

        val text = markersCount.toString()
        paint.getTextBounds(text, 0, text.length, bounds)
        val x = bitmap.width / 2.0f
        val y = (bitmap.height - bounds.height()) / 2.0f - bounds.top

        val canvas = Canvas(bitmap)
        canvas.drawText(text, x, y, paint)

        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        cache.put(markersCount, icon)

        return clusterOptions.icon(icon)
    }

    companion object {
        private val markerDrawables =
            intArrayOf(R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4, R.drawable.m5)

        private val markerCounts = intArrayOf(10, 100, 1000, 10000, Integer.MAX_VALUE)
    }
}