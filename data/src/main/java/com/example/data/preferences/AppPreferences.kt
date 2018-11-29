package com.example.data.preferences

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(context: Context) {
    var radius: Int by DefaultSharedPreferences(context, PreferencesEntry.PlaceSearchRadius)
}