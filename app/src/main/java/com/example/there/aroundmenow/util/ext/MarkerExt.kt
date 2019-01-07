package com.example.there.aroundmenow.util.ext

import com.androidmapsextensions.ClusterGroup
import com.androidmapsextensions.Marker

fun Marker.tryResetClusterGroup() = try {
    clusterGroup = ClusterGroup.DEFAULT
} catch (e: Exception) {
}