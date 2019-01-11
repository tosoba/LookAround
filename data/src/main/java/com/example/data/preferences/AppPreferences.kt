package com.example.data.preferences

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(context: Context) {
    var placesSearchRadius: String by SharedPreference(context, PreferencesEntry.PlacesSearchRadius)
    var defaultVisualizer: String by SharedPreference(context, PreferencesEntry.DefaultVisualizer)
    var cameraTopEdgePositionPx: Int by SharedPreference(context, PreferencesEntry.CameraTopEdgePositionPx)
    var cameraBottomEdgePositionVerticalPx: Int by SharedPreference(
        context,
        PreferencesEntry.CameraBottomEdgePositionVerticalPx
    )
    var cameraBottomEdgePositionHorizontalPx: Int by SharedPreference(
        context,
        PreferencesEntry.CameraBottomEdgePositionHorizontalPx
    )
    var screenHeightVerticalPx: Int by SharedPreference(context, PreferencesEntry.ScreenHeightVerticalPx)
    var screenHeightHorizontalPx: Int by SharedPreference(context, PreferencesEntry.ScreenHeightHorizontalPx)
}