package com.example.there.aroundmenow.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.there.aroundmenow.R

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)
    }
}