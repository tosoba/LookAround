package com.example.there.aroundmenow.util.view.map

import com.example.there.aroundmenow.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import io.reactivex.subjects.PublishSubject


interface GoogleMapInitializer : OnMapReadyCallback {
    val googleMapFragment: SupportMapFragment?

    var map: GoogleMap

    fun initializeMapFragment() {
        googleMapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                googleMapFragment?.context, R.raw.map_style
            )
        )
    }
}

interface GoogleMapRxInitializer : GoogleMapInitializer {
    val mapInitialized: PublishSubject<Unit>

    override fun onMapReady(googleMap: GoogleMap) {
        super.onMapReady(googleMap)
        mapInitialized.onNext(Unit)
    }
}