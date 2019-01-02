package com.example.there.aroundmenow.visualizer.map

import android.os.Bundle
import android.view.View
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.util.ext.dpToPx
import com.example.there.aroundmenow.util.ext.latLngBounds
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.lifecycle.EventBusComponent
import com.example.there.aroundmenow.util.view.map.GoogleMapRxInitializer
import com.example.there.aroundmenow.visualizer.VisualizerState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class VisualizerMapFragment : RxFragment.Stateless.HostAware.WithLayout<Unit, VisualizerState>(
    R.layout.fragment_visualizer_map,
    HostAwarenessMode.PARENT_FRAGMENT_ONLY
), GoogleMapRxInitializer {

    override lateinit var map: GoogleMap

    override val googleMapFragment: SupportMapFragment?
        get() = if (isAdded)
            childFragmentManager.findFragmentById(R.id.visualizer_google_map_fragment) as? SupportMapFragment
        else null

    override val mapInitialized: PublishSubject<Unit> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += EventBusComponent(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMapFragment()
    }

    override fun Observable<VisualizerState>.observeParentFragment() = map { it.places }
        .delaySubscription(mapInitialized)
        .distinctUntilChanged()
        .subscribeWithAutoDispose { placesState ->
            if (placesState is ViewDataState.Value && this@VisualizerMapFragment::map.isInitialized) {
                placesState.value.forEach { place -> map.addMarker(place.markerOptions) }
                map.animateCamera(
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
}

