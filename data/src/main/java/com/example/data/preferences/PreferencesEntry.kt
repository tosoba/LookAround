package com.example.data.preferences

sealed class PreferencesEntry<T>(
    val key: String,
    val defaultValue: T,
    val options: PreferencesEntry.Options
) {
    operator fun component1(): String = key
    operator fun component2(): T = defaultValue

    object PlaceSearchRadius :
        PreferencesEntry<Int>("PREF_KEY_SEARCH_RADIUS", 10000, Options.DEFINED_BY_USER)

    object ScreenHeightVerticalPx :
        PreferencesEntry<Int>("PREF_SCREEN_HEIGHT_VERTICAL", 0, Options.CALCULATED_ON_APP_START)

    object ScreenHeightHorizontalPx :
        PreferencesEntry<Int>("PREF_SCREEN_HEIGHT_HORIZONTAL", 0, Options.CALCULATED_ON_APP_START)

    object CameraTopEdgePositionPx :
        PreferencesEntry<Int>("PREF_KEY_TOP_EDGE", 0, Options.CALCULATED_ON_APP_START)

    object CameraBottomEdgePositionVerticalPx :
        PreferencesEntry<Int>("PREF_KEY_BOTTOM_EDGE_VERTICAL", 0, Options.CALCULATED_ON_APP_START)

    object CameraBottomEdgePositionHorizontalPx :
        PreferencesEntry<Int>("PREF_KEY_BOTTOM_EDGE_HORIZONTAL", 0, Options.CALCULATED_ON_APP_START)

    class Options private constructor(
        val initializeWithDefault: Boolean,
        val mutableAfterOnceSet: Boolean,
        val throwIfUnset: Boolean
    ) {
        companion object {
            val DEFINED_BY_USER: Options = Options(true, true, false)
            val CALCULATED_ON_APP_START: Options = Options(false, false, true)
        }
    }
}