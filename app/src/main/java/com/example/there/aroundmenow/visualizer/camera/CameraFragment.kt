package com.example.there.aroundmenow.visualizer.camera

import android.os.Bundle
import android.view.View
import com.example.data.preferences.AppPreferences
import com.example.there.appuntalib.orientation.OrientationManager
import com.example.there.appuntalib.point.Point
import com.example.there.appuntalib.ui.CameraView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.util.ext.defaultLocation
import com.example.there.aroundmenow.util.ext.location
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.lifecycle.OrientationManagerComponent
import com.example.there.aroundmenow.visualizer.VisualizerState
import com.example.there.aroundmenow.visualizer.camera.CameraState.Constants.cameraRanges
import com.example.there.aroundmenow.visualizer.camera.view.*
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_camera.*
import javax.inject.Inject


class CameraFragment : RxFragment.HostAware.WithLayout<CameraState, MainState, VisualizerState, CameraActions>(
    R.layout.fragment_camera,
    HostAwarenessMode.BOTH
), AppuntaView.Controller {

    override val radarView: CameraRadarView?
        get() = radar_view

    override val cameraView: com.example.there.aroundmenow.visualizer.camera.view.CameraView?
        get() = camera_view

    @Inject
    lateinit var appPreferences: AppPreferences

    private val cameraParams: CameraParams by lazy {
        CameraParams(
            screenHeightPx = appPreferences.screenHeightPx,
            cameraTopEdgePositionPx = appPreferences.cameraTopEdgePositionPx,
            cameraBottomEdgePositionPx = appPreferences.cameraBottomEdgePositionPx
        )
    }

    private val cameraRenderer: CameraRenderer by lazy { CameraRenderer(cameraParams) }

    private val orientationManager: OrientationManager by lazy {
        OrientationManager(activity).apply {
            axisMode = OrientationManager.MODE_AR
            setOnOrientationChangeListener {
                camera_view?.orientation = it
                camera_view?.phoneRotation = OrientationManager.getPhoneRotation(activity)
                radar_view?.orientation = it
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CameraUtils.resetObjectIdGenerator()
        lifecycle += OrientationManagerComponent(orientationManager, activity!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun Observable<CameraState>.observe() {
        map { it.page }.distinctUntilChanged().subscribeWithAutoDispose {
            cameraRenderer.currentPage = it

            camera_page_number_text_view?.text = it.toString()
            page_down_btn?.isEnabled = it > 0
        }

        map { it.rangeIndex }.distinctUntilChanged().subscribeWithAutoDispose {
            val range = cameraRanges.keys.elementAt(it)

            updateRange(range.toDouble())

            fab_cam_menu.menuButtonLabelText = "Cam range: " + cameraRanges[range]
            max_distance_minus_btn?.isEnabled = it > 0
            max_distance_plus_btn?.isEnabled = it < cameraRanges.size - 1
        }
    }

    override fun Observable<MainState>.observeActivity() = map { it.userLatLng }.subscribeWithAutoDispose {
        when (it) {
            is ViewDataState.Value -> {
                updateLocation(it.value.location)
                cameraRenderer.userLatLng = it.value
            }
        }
    }

    override fun Observable<VisualizerState>.observeParentFragment() =
        map { it.places }.subscribeWithAutoDispose { placesState ->
            when (placesState) {
                is ViewDataState.Value -> with(placesState.value.map {
                    CameraObject(
                        it,
                        cameraRenderer,
                        cameraParams
                    )
                }) {
                    updatePoints(map { it.point })
                    updateRadarPoints(map { it.radarPoint })

                    //TODO: if there's only one place (single POI) - set range equal to distance > POI's distance and hide range controls - else make range controls visible
                }
            }
        }

    override fun observeViews() {
        page_up_btn.clicks().subscribeWithAutoDispose { actions.pageUp() }
        page_down_btn.clicks().subscribeWithAutoDispose { actions.pageDown() }

        max_distance_plus_btn.clicks().subscribeWithAutoDispose { actions.rangeUp() }
        max_distance_minus_btn.clicks().subscribeWithAutoDispose { actions.rangeDown() }
    }

    private fun initViews() {
        initCameraView()
        initCameraLayout()
        initRadarView()
    }

    private fun initCameraView() = with(camera_view) {
        setPoints(ArrayList<Point>())
        setPosition(defaultLocation)
    }

    private fun initRadarView() = with(radar_view) {
        setPoints(ArrayList<Point>())
        rotableBackground = R.drawable.radar_arrow
        setPosition(defaultLocation)
    }

    private fun initCameraLayout() {
        val cameraView = CameraView(context, object : OnCameraPreview {
            override fun onPreview() {
                camera_loading_progressbar?.visibility = View.GONE
            }
        })

        camera_layout?.addView(cameraView)
    }
}
