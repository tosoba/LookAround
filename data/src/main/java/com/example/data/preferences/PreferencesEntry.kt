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

    object CameraTopEdgePositionPx :
        PreferencesEntry<Int>("PREF_KEY_TOP_EDGE", 0, Options.CALCULATED_ON_APP_START)

    object CameraBottomEdgePositionPx :
        PreferencesEntry<Int>("PREF_KEY_BOTTOM_EDGE", 0, Options.CALCULATED_ON_APP_START)

    object ScreenHeightPx :
        PreferencesEntry<Int>("PREF_SCREEN_HEIGHT", 0, Options.CALCULATED_ON_APP_START)

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