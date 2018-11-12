package com.example.there.aroundmenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.places.PlacesFragment

class MainActivity : AppCompatActivity() {

    private val currentlyShowingFragment: Fragment?
        get() = supportFragmentManager?.findFragmentById(backStackLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showPlacesFragmentIfNotAlreadyShown()
    }

    fun showFragment(fragment: Fragment, addToBackStack: Boolean) = with(supportFragmentManager.beginTransaction()) {
        setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        replace(backStackLayoutId, fragment)
        if (addToBackStack) addToBackStack(null)
        commit()
    }

    private fun showPlacesFragmentIfNotAlreadyShown() {
        if (currentlyShowingFragment == null) with(supportFragmentManager.beginTransaction()) {
            add(backStackLayoutId, PlacesFragment())
            commit()
        }
    }

    companion object {
        private const val backStackLayoutId = R.id.container
    }
}
