package com.example.there.aroundmenow.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxActivity
import com.example.there.aroundmenow.places.PlacesFragment
import com.example.there.aroundmenow.util.ext.toggle
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener
import dagger.android.AndroidInjector
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : RxActivity.Layout<MainState, MainActions>(R.layout.activity_main) {

    private val currentlyShowingFragment: Fragment?
        get() = supportFragmentManager?.findFragmentById(backStackLayoutId)

    private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        updateHomeAsUpIndicator()
    }

    private val locationAccessIsNeededMsg: String by lazy { getString(R.string.location_access_is_needed) }
    private val cameraAccessIsNeededMsg: String by lazy { getString(R.string.camera_access_is_needed) }
    private val allPermissionsAreNeededMsg: String by lazy { getString(R.string.all_permissions_are_needed) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showPlacesFragmentIfNotAlreadyShown()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.menu)
        }

        supportFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)

        drawer_navigation_view.onItemWithIdSelected { drawer_layout.closeDrawers() }

        checkInitialPermissions()
    }

    override fun onResume() {
        super.onResume()
        updateHomeAsUpIndicator()
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
        commitAllowingStateLoss()
    }

    fun removeFragment() = supportFragmentManager.popBackStack()

    //TODO: test behaviour when permissions are not granted
    fun checkPermissionsAndThen(block: () -> Unit) = Dexter.withActivity(this)
        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) block()
                else when (report.deniedPermissionResponses.size) {
                    1 -> when (report.deniedPermissionResponses.first().permissionName) {
                        Manifest.permission.CAMERA -> showPermissionsSnackbar(cameraAccessIsNeededMsg)
                        Manifest.permission.ACCESS_FINE_LOCATION -> showPermissionsSnackbar(
                            locationAccessIsNeededMsg
                        )
                    }
                    2 -> showPermissionsSnackbar(allPermissionsAreNeededMsg)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest>,
                token: PermissionToken
            ) = token.continuePermissionRequest()
        })
        .check()

    private fun showPermissionsSnackbar(
        message: String
    ) = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.settings)) { startActivity(Intent(Settings.ACTION_SETTINGS)) }
        .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        .run {
            duration = BaseTransientBottomBar.LENGTH_LONG
            show()
        }

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

    private fun checkInitialPermissions() = with(Dexter.withActivity(this)) {
        withPermission(Manifest.permission.CAMERA)
            .withListener(initialPermissionsListener(getString(R.string.camera_access_is_needed)))
            .check()

        withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(initialPermissionsListener(getString(R.string.location_access_is_needed)))
            .check()
    }

    private fun initialPermissionsListener(
        message: String
    ): PermissionListener = SnackbarOnDeniedPermissionListener.Builder
        .with(container, message)
        .withOpenSettingsButton(getString(R.string.settings))
        .build()

    companion object {
        private const val backStackLayoutId = R.id.container

        private const val PLACE_AUTOCOMPLETE_ACTIVITY_REQUEST_CODE = 100
    }
}
