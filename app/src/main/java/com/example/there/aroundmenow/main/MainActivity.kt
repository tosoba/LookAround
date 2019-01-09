package com.example.there.aroundmenow.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxActivity
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.databinding.PlaceAutocompleteResultDialogBinding
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.placedetails.PlaceDetailsFragment
import com.example.there.aroundmenow.places.PlacesFragment
import com.example.there.aroundmenow.util.AppConstants
import com.example.there.aroundmenow.util.ext.*
import com.example.there.aroundmenow.util.lifecycle.RxLocationComponent
import com.example.there.aroundmenow.visualizer.VisualizerFragment
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.android.AndroidInjector
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject
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

    private val autocompletePlaceDetailsRequest: PublishSubject<Intent> = PublishSubject.create()
    private val autocompleteVisualizeRequest: PublishSubject<Place> = PublishSubject.create()

    private var lastBottomSheetDialog: BottomSheetDialog? = null

    private val rxLocationComponent: RxLocationComponent by lazy {
        RxLocationComponent(
            this,
            ::showGooglePlayServicesSnackbar,
            actions::setUserLatLng,
            ::showLocationDisabledSnackbar
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        initActionBar()

        observeInternetConnectivity { actions.setConnectedToInternet(it) }
        lifecycle += rxLocationComponent

        supportFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)

        drawer_navigation_view.onItemWithIdSelected { drawer_layout.closeDrawers() }

        disableScreenRotation()
        checkPermissions(
            onAnyResult = {
                enableScreenRotation()
                showPlacesFragmentIfNotAlreadyShown()
            },
            onGranted = rxLocationComponent::tryStartUpdates
        )
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
        when (requestCode) {
            PLACE_AUTOCOMPLETE_ACTIVITY_REQUEST_CODE -> when (resultCode) {
                Activity.RESULT_OK -> showAutocompleteResultDialog(intent = data!!)

                PlaceAutocomplete.RESULT_ERROR -> Toast.makeText(
                    this,
                    "Place Autocomplete error.",
                    Toast.LENGTH_LONG
                ).show()
            }

            LOCATION_SETTINGS_REQUEST_CODE -> rxLocationComponent.tryStartUpdates()
        }
    }

    override fun Observable<MainState>.observe() {
        autocompleteVisualizeRequest.withLatestFrom(this)
            .subscribeWithAutoDispose { (place, mainState) ->
                if (mainState.userLatLng is ViewDataState.Value) checkPermissions(onGranted = {
                    startLocationUpdatesIfNotStartedYet()
                    showFragment(
                        VisualizerFragment.with(
                            VisualizerFragment.Arguments.Places(
                                listOf(
                                    UISimplePlace.fromGooglePlaceWithUserLatLng(
                                        place,
                                        mainState.userLatLng.value
                                    )
                                )
                            )
                        ), true
                    )
                })
                else Toast.makeText(
                    this@MainActivity,
                    "Cannot show the place on camera - location unavailable.",
                    Toast.LENGTH_LONG
                ).show()

                lastBottomSheetDialog?.dismiss()
            }

        autocompletePlaceDetailsRequest.withLatestFrom(this)
            .subscribeWithAutoDispose { (intent, mainState) ->
                if (mainState.userLatLng is ViewDataState.Value) showFragment(
                    PlaceDetailsFragment.with(
                        PlaceDetailsFragment.Arguments.PlaceAutocompleteIntent(
                            intent,
                            UISimplePlace.fromGooglePlaceWithUserLatLng(
                                PlaceAutocomplete.getPlace(this@MainActivity, intent),
                                mainState.userLatLng.value
                            )
                        )
                    ), true
                )
                else Toast.makeText(
                    this@MainActivity,
                    "Cannot show place details - location unavailable.",
                    Toast.LENGTH_LONG
                ).show()

                lastBottomSheetDialog?.dismiss()
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

    fun checkPermissions(
        onGranted: (() -> Unit)? = null,
        onAnyResult: (() -> Unit)? = null
    ) = Dexter.withActivity(this)
        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                onAnyResult?.invoke()

                if (report.areAllPermissionsGranted()) onGranted?.invoke()
                else when (report.deniedPermissionResponses.size) {
                    1 -> when (report.deniedPermissionResponses.first().permissionName) {
                        Manifest.permission.CAMERA -> showPermissionsSnackbar(cameraAccessIsNeededMsg)
                        Manifest.permission.ACCESS_FINE_LOCATION -> showPermissionsSnackbar(locationAccessIsNeededMsg)
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

    fun startLocationUpdatesIfNotStartedYet() = rxLocationComponent.tryStartUpdates()

    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun initActionBar() {
        setSupportActionBar(main_toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.menu)
        }
    }

    private fun showPermissionsSnackbar(
        message: String
    ) = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.settings)) { startActivity(Intent(Settings.ACTION_SETTINGS)) }
        .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        .run {
            duration = BaseTransientBottomBar.LENGTH_LONG
            showWithBottomMargin(dpToPx(AppConstants.BOTTOM_NAVIGATION_VIEW_HEIGHT_DP.toFloat()).toInt())
        }

    private fun showPlacesFragmentIfNotAlreadyShown() {
        if (currentlyShowingFragment == null) with(supportFragmentManager.beginTransaction()) {
            add(backStackLayoutId, PlacesFragment())
            commitAllowingStateLoss()
        }
    }

    private fun startPlaceAutocompleteActivity() = startActivityForResult(
        PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this),
        PLACE_AUTOCOMPLETE_ACTIVITY_REQUEST_CODE
    )

    private fun updateHomeAsUpIndicator() = if (supportFragmentManager.backStackEntryCount > 0)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
    else supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)

    private fun showAutocompleteResultDialog(intent: Intent) {
        lastBottomSheetDialog = BottomSheetDialog(this).apply {
            setContentView(
                DataBindingUtil.inflate<PlaceAutocompleteResultDialogBinding>(
                    layoutInflater,
                    R.layout.place_autocomplete_result_dialog,
                    null,
                    false
                ).apply {
                    this.place = place
                    autocompleteResultVisualizeButton.setOnClickListener {
                        autocompleteVisualizeRequest.onNext(PlaceAutocomplete.getPlace(this@MainActivity, intent))
                    }
                    autocompleteResultDetailsButton.setOnClickListener { autocompletePlaceDetailsRequest.onNext(intent) }
                }.root
            )
            show()
        }
    }


    private fun showGooglePlayServicesSnackbar() = Snackbar.make(
        findViewById(R.id.container),
        "Google Play Services unavailable. Features requiring device location will not work.",
        Snackbar.LENGTH_LONG
    ).run {
        duration = BaseTransientBottomBar.LENGTH_INDEFINITE
        showWithBottomMargin(dpToPx(AppConstants.BOTTOM_NAVIGATION_VIEW_HEIGHT_DP.toFloat()).toInt())
    }

    private fun showLocationDisabledSnackbar() = Snackbar.make(
        findViewById(R.id.container), "Location disabled.", Snackbar.LENGTH_INDEFINITE
    ).setAction(getString(R.string.settings)) {
        startActivityForResult(
            Intent(Settings.ACTION_SETTINGS),
            LOCATION_SETTINGS_REQUEST_CODE
        )
    }.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent)).run {
        duration = BaseTransientBottomBar.LENGTH_LONG
        showWithBottomMargin(dpToPx(AppConstants.BOTTOM_NAVIGATION_VIEW_HEIGHT_DP.toFloat()).toInt())
    }

    companion object {
        private const val backStackLayoutId = R.id.container

        private const val PLACE_AUTOCOMPLETE_ACTIVITY_REQUEST_CODE = 100
        private const val LOCATION_SETTINGS_REQUEST_CODE = 200
    }
}
