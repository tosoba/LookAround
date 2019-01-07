package com.example.there.aroundmenow.visualizer.map

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.androidmapsextensions.*
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.placedetails.PlaceDetailsFragment
import com.example.there.aroundmenow.util.ext.*
import com.example.there.aroundmenow.util.lifecycle.EventBusComponent
import com.example.there.aroundmenow.util.view.map.GoogleMapWrapperLayout
import com.example.there.aroundmenow.util.view.map.MapClusterOptionsProvider
import com.example.there.aroundmenow.util.view.map.OnInfoWindowElementTouchListener
import com.example.there.aroundmenow.visualizer.VisualizerState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_visualizer_map.*
import kotlinx.android.synthetic.main.map_marker_info_window.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class VisualizerMapFragment :
    RxFragment.Stateful.HostAware.WithLayout<VisualizerMapState, Unit, VisualizerState, VisualizerMapActions>(
        R.layout.fragment_visualizer_map,
        HostAwarenessMode.PARENT_FRAGMENT_ONLY
    ), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private val googleMapFragment: SupportMapFragment?
        get() = if (isAdded)
            childFragmentManager.findFragmentById(R.id.visualizer_google_map_fragment) as? SupportMapFragment
        else null

    private val mapInitialized: PublishSubject<Unit> = PublishSubject.create()

    private val declusterifiedMarkers = ArrayList<Marker>()

    private val clusteringSettings: ClusteringSettings by lazy {
        ClusteringSettings()
            .clusterOptionsProvider(MapClusterOptionsProvider(resources))
            .addMarkersDynamically(true)
    }

    private val markerInfoWindow: ViewGroup by lazy {
        layoutInflater.inflate(R.layout.map_marker_info_window, null) as ViewGroup
    }

    private val onInfoWindowBtnClickedListener: OnInfoWindowElementTouchListener by lazy {
        object : OnInfoWindowElementTouchListener(
            markerInfoWindow.marker_info_window_show_details_btn,
            ContextCompat.getDrawable(context!!, R.drawable.map_marker_button_background)!!,
            ContextCompat.getDrawable(context!!, R.drawable.map_marker_button_background_pressed)!!
        ) {
            override fun onClickConfirmed(v: View, marker: Marker) =
                actions.markerSelected(marker)
        }
    }

    private val infoWindowAdapter: GoogleMap.InfoWindowAdapter by lazy {
        object : GoogleMap.InfoWindowAdapter {
            override fun getInfoContents(marker: Marker): View? = null

            override fun getInfoWindow(marker: Marker): View = markerInfoWindow.apply {
                marker_info_window_title_text_view.text = marker.title
                marker_info_window_snippet_text_view.text = marker.snippet
                onInfoWindowBtnClickedListener.marker = marker
                marker_info_window_show_details_btn.setOnTouchListener(onInfoWindowBtnClickedListener)
                visualizer_map_wrapper_layout.setMarkerWithInfoWindow(marker, this)
            }
        }
    }

    private val onMarkerClickListener: GoogleMap.OnMarkerClickListener by lazy {
        GoogleMap.OnMarkerClickListener { marker ->
            if (marker.isCluster) {
                val builder = LatLngBounds.builder()
                marker.markers.forEach { builder.include(it.position) }
                val bounds = builder.build()
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
                declusterify(marker)
            } else {
                val projection = map.projection
                val markerPoint = projection.toScreenLocation(marker.position)
                markerPoint.offset(0, resources.getDimensionPixelSize(R.dimen.map_dy))
                val newLatLng = projection.fromScreenLocation(markerPoint)
                map.moveCamera(CameraUpdateFactory.newLatLng(newLatLng))
                marker.showInfoWindow()
            }
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += EventBusComponent(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleMapFragment?.getExtendedMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        with(googleMap) {
            setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    googleMapFragment?.context, R.raw.map_style
                )
            )
            mapInitialized.onNext(Unit)

            setClustering(clusteringSettings)

            visualizer_map_wrapper_layout?.init(
                this,
                context!!.dpToPx(GoogleMapWrapperLayout.DEFAULT_BOTTOM_OFFSET_DP.toFloat()).toInt()
            )
            setInfoWindowAdapter(infoWindowAdapter)
            setOnMarkerClickListener(onMarkerClickListener)
            setOnInfoWindowCloseListener { actions.markerInfoWindowDismissed() }
        }
    }

    override fun Observable<VisualizerMapState>.observe() = map { it.selectedMarker }
        .distinctUntilChanged()
        .withLatestFrom(observableParentFragmentState.map { it.places })
        .subscribeWithAutoDispose { (selectedMarkerState, placesState) ->
            if (selectedMarkerState is ViewDataState.Value && placesState is ViewDataState.Value) {
                placesState.value.find { it.name == selectedMarkerState.value.title }?.let {
                    selectedMarkerState.value.hideInfoWindow()
                    mainActivity?.showFragment(
                        PlaceDetailsFragment.with(PlaceDetailsFragment.Arguments.SimplePlace(it)),
                        true
                    )
                }
            }
        }

    override fun Observable<VisualizerState>.observeParentFragment() = map { it.places }
        .delaySubscription(mapInitialized)
        .distinctUntilChanged()
        .subscribeWithAutoDispose { placesState ->
            if (placesState is ViewDataState.Value && this@VisualizerMapFragment::map.isInitialized) {
                placesState.value.forEach { place -> map.addMarker(place.extendedMarkerOptions) }
                map.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        placesState.value.latLngBounds,
                        context?.dpToPx(10f)?.toInt() ?: 25
                    )
                )
            }
        }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onZoomToPlaceEvent(event: MapZoomToPlaceEvent) {
        if (::map.isInitialized) map.animateCamera(CameraUpdateFactory.newLatLngZoom(event.place.latLng, 16f))
    }

    private fun declusterify(cluster: Marker) {
        clusterifyMarkers()
        declusterifiedMarkers.addAll(cluster.markers)
        declusterifiedMarkers.forEach { it.tryResetClusterGroup() }
    }

    private fun clusterifyMarkers() {
        if (declusterifiedMarkers.isNotEmpty()) {
            declusterifiedMarkers.forEach { it.tryResetClusterGroup() }
            declusterifiedMarkers.clear()
        }
    }
}
