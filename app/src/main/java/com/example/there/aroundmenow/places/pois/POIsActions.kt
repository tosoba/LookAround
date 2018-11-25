package com.example.there.aroundmenow.places.pois

import com.example.there.aroundmenow.base.architecture.RxViewModelHolder
import com.google.android.gms.maps.model.LatLng

interface POIsActions : RxViewModelHolder<POIsState, POIsViewModel> {
    fun findPOIsNearby(latLng: LatLng)
}