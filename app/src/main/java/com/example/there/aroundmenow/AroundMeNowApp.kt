package com.example.there.aroundmenow

import android.app.Activity
import android.app.Application
import com.example.data.preferences.AppPreferences
import com.example.there.aroundmenow.di.AppInjector
import com.example.there.aroundmenow.util.AppConstants
import com.example.there.aroundmenow.util.ext.dpToPx
import com.example.there.aroundmenow.util.ext.screenDimensionsPx
import com.example.there.aroundmenow.visualizer.camera.view.CameraObjectDialogConstants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class AroundMeNowApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)

        initCameraPreferences()
    }

    private fun initCameraPreferences() = with(appPreferences) {
        val (_, screenHeight) = screenDimensionsPx
        cameraBottomEdgePositionPx =
                (screenHeight - dpToPx(AppConstants.BOTTOM_NAVIGATION_VIEW_HEIGHT_DP.toFloat())).toInt()
        cameraTopEdgePositionPx =
                (CameraObjectDialogConstants.HEIGHT / 2 + resources.getDimensionPixelSize(R.dimen.radar_layout_dimension) + 2 * resources.getDimensionPixelSize(
                    R.dimen.radar_margin
                )).toInt()
        screenHeightPx = screenHeight
    }
}