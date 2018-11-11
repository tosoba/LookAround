package com.example.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context).also { preferences ->
            preferences.edit().apply {
                if (!preferences.contains(Keys.SEARCH_RADIUS))
                    putInt(Keys.SEARCH_RADIUS, DefaultValues.SEARCH_RADIUS).apply()
            }
        }

    var radius: Int
        set(value) = sharedPreferences.edit().putInt(Keys.SEARCH_RADIUS, value).apply()
        get() = sharedPreferences.getInt(Keys.SEARCH_RADIUS, DefaultValues.SEARCH_RADIUS)

    object Keys {
        const val SEARCH_RADIUS = "PREF_KEY_SEARCH_RADIUS"
    }

    object DefaultValues {
        const val SEARCH_RADIUS = 5000
    }
}