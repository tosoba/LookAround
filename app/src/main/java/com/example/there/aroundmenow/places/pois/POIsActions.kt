package com.example.there.aroundmenow.places.pois

import com.example.domain.task.error.FindPlaceDetailsError
import com.example.there.aroundmenow.base.architecture.view.ViewDataSideEffect
import com.example.there.aroundmenow.model.UISimplePlace
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng

interface POIsActions {
    fun findPOIsNearby(latLng: LatLng)
    fun findPlaceDetails(place: UISimplePlace, sideEffect: ViewDataSideEffect<Place, FindPlaceDetailsError>)
}