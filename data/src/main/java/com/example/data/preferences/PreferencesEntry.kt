package com.example.data.preferences

sealed class PreferencesEntry<T>(
    val key: String,
    val defaultValue: T
) {
    operator fun component1(): String = key
    operator fun component2(): T = defaultValue

    object PlaceSearchRadius : PreferencesEntry<Int>("PREF_KEY_SEARCH_RADIUS", 10000)
}