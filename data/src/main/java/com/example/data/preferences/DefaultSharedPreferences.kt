package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DefaultSharedPreferences<T>(
    context: Context,
    private val entry: PreferencesEntry<T>
) : ReadWriteProperty<Any?, T> {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        if (!preferences.contains(entry.key)) put(entry.defaultValue)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = with(preferences) {
        val (key, defaultValue) = entry
        val res: Any = when (defaultValue) {
            is Long -> getLong(key, defaultValue)
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            else -> throw IllegalArgumentException(INVALID_TYPE_EXCEPTION_MSG)
        }
        @Suppress("UNCHECKED_CAST")
        res as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = put(value)

    private fun put(value: T) = with(preferences.edit()) {
        val (key, _) = entry
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalArgumentException(INVALID_TYPE_EXCEPTION_MSG)
        }.apply()
    }

    companion object {
        private const val INVALID_TYPE_EXCEPTION_MSG = "This type cannot be saved into Preferences"
    }
}