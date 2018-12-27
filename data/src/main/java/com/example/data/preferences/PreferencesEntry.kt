package com.example.data.preferences

sealed class PreferencesEntry<T>(
    val key: String,
    val defaultValue: T
) {
    operator fun component1(): String = key
    operator fun component2(): T = defaultValue

    object PlaceSearchRadius : PreferencesEntry<Int>("PREF_KEY_SEARCH_RADIUS", 10000)
    object CameraTopEdgePositionPx : PreferencesEntry<Int>("PREF_KEY_TOP_EDGE", 0)
    object CameraBottomEdgePositionPx : PreferencesEntry<Int>("PREF_KEY_BOTTOM_EDGE", 0)
    object ScreenHeightPx : PreferencesEntry<Int>("PREF_SCREEN_HEIGHT", 0)
}