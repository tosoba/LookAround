package com.example.there.lookaround.preferences

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.example.there.lookaround.R
import com.example.there.lookaround.util.AppConstants
import com.example.there.lookaround.util.ext.dpToPx

class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val params = view.layoutParams as? ViewGroup.MarginLayoutParams
        params?.let {
            it.setMargins(
                it.leftMargin,
                it.topMargin + view.context.dpToPx(AppConstants.TOOLBAR_HEIGHT_DP.toFloat()).toInt(),
                it.rightMargin,
                it.bottomMargin
            )
            view.layoutParams = it
        }
    }
}