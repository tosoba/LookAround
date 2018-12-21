package com.example.there.aroundmenow.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxActivity
import com.example.there.aroundmenow.places.PlacesFragment
import com.example.there.aroundmenow.util.ext.toggle
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import dagger.android.AndroidInjector
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : RxActivity.Layout<MainState, MainActions>(R.layout.activity_main) {

    private val currentlyShowingFragment: Fragment?
        get() = supportFragmentManager?.findFragmentById(backStackLayoutId)

    private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        updateHomeAsUpIndicator()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showPlacesFragmentIfNotAlreadyShown()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.menu)
        }

        supportFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)

        drawer_navigation_view.onItemWithIdSelected { drawer_layout.closeDrawers() }
    }

    override fun onResume() {
        super.onResume()
        updateHomeAsUpIndicator()
    }

    override fun Observable<MainState>.observe() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    @SuppressLint("RtlHardcoded")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            if (supportFragmentManager.backStackEntryCount > 0) onBackPressed()
            else drawer_layout?.toggle(Gravity.LEFT)
            true
        }
        R.id.search_places_toolbar_menu_item -> {
            startPlaceAutocompleteActivity()
            true
        }
        else -> false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_AUTOCOMPLETE_ACTIVITY_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                }
                PlaceAutocomplete.RESULT_ERROR -> {
                }
                Activity.RESULT_CANCELED -> {
                }
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    fun showFragment(
        fragment: Fragment,
        addToBackStack: Boolean
    ) = with(supportFragmentManager.beginTransaction()) {
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

    fun removeFragment() = supportFragmentManager.popBackStack()

    private fun showPlacesFragmentIfNotAlreadyShown() {
        if (currentlyShowingFragment == null) with(supportFragmentManager.beginTransaction()) {
            add(backStackLayoutId, PlacesFragment())
            commit()
        }
    }

    private fun startPlaceAutocompleteActivity() = startActivityForResult(
        PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this),
        PLACE_AUTOCOMPLETE_ACTIVITY_REQUEST_CODE
    )

    private fun updateHomeAsUpIndicator() = if (supportFragmentManager.backStackEntryCount > 0)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
    else supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)

    companion object {
        private const val backStackLayoutId = R.id.container

        private const val PLACE_AUTOCOMPLETE_ACTIVITY_REQUEST_CODE = 100
    }
}
