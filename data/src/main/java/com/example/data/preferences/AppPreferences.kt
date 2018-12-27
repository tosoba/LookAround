package com.example.data.preferences

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(context: Context) {

    var radius: Int by SharedPreference(context, PreferencesEntry.PlaceSearchRadius)

    var cameraTopEdgePositionPx: Int by SharedPreference(
        context,
        PreferencesEntry.CameraTopEdgePositionPx,
        false,
        true
    )

    var cameraBottomEdgePositionPx: Int by SharedPreference(
        context,
        PreferencesEntry.CameraBottomEdgePositionPx,
        false,
        true
    )

    var screenHeightPx: Int by SharedPreference(
        context,
        PreferencesEntry.ScreenHeightPx,
        false,
        true
    )
}