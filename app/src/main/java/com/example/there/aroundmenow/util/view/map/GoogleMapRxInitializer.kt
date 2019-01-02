package com.example.there.aroundmenow.util.view.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import io.reactivex.subjects.PublishSubject

interface GoogleMapInitializer : OnMapReadyCallback {
    val googleMapFragment: SupportMapFragment?

    var map: GoogleMap

    fun initializeMapFragment() {
        googleMapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}

interface GoogleMapRxInitializer : GoogleMapInitializer {
    val mapInitialized: PublishSubject<Unit>

    override fun onMapReady(googleMap: GoogleMap) {
        mapInitialized.onNext(Unit)
    }
}