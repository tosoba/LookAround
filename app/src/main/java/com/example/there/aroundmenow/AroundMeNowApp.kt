package com.example.there.aroundmenow

import android.app.Activity
import android.app.Application
import com.example.data.preferences.AppPreferences
import com.example.there.aroundmenow.di.AppInjector
import com.example.there.aroundmenow.util.AppConstants
import com.example.there.aroundmenow.util.ext.ScreenOrientation
import com.example.there.aroundmenow.util.ext.dpToPx
import com.example.there.aroundmenow.util.ext.orientation
import com.example.there.aroundmenow.util.ext.screenDimensionsPx
import com.example.there.aroundmenow.util.rx.RxHandlers
import com.example.there.aroundmenow.visualizer.camera.view.CameraObjectDialogConstants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.plugins.RxJavaPlugins
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

        initAppRxErrorHandler()
    }

    private fun initCameraPreferences() = with(appPreferences) {
        val (screenWidth, screenHeight) = screenDimensionsPx

        val shortScreenEdge = if (orientation == ScreenOrientation.HORIZONTAL)
            screenHeight else screenWidth
        val longScreenEdge = if (orientation == ScreenOrientation.HORIZONTAL)
            screenWidth else screenHeight

        cameraBottomEdgePositionVerticalPx =
                (longScreenEdge - dpToPx(AppConstants.BOTTOM_NAVIGATION_VIEW_HEIGHT_DP.toFloat())).toInt()

        cameraBottomEdgePositionHorizontalPx =
                (shortScreenEdge - dpToPx(AppConstants.BOTTOM_NAVIGATION_VIEW_HEIGHT_DP.toFloat())).toInt()

        cameraTopEdgePositionPx =
                (CameraObjectDialogConstants.HEIGHT / 2 + dpToPx(AppConstants.TOOLBAR_HEIGHT_DP.toFloat())).toInt()

        screenHeightVerticalPx = longScreenEdge
        screenHeightHorizontalPx = shortScreenEdge

        cameraGridNumberOfRowsVertical = cameraBottomEdgePositionVerticalPx / cameraTopEdgePositionPx
        cameraGridNumberOfRowsHorizontal = cameraBottomEdgePositionHorizontalPx / cameraTopEdgePositionPx
    }

    private fun initAppRxErrorHandler() {
        RxJavaPlugins.setErrorHandler(RxHandlers.Exception.loggingConsumer)
    }
}