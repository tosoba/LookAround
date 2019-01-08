package com.example.there.aroundmenow.placedetails

import com.example.there.aroundmenow.model.UISimplePlace
import com.google.android.gms.location.places.Place

interface PlaceDetailsActions {
    fun findPlaceDetails(place: UISimplePlace)
    fun findPlacePhotos(id: String)
    fun setPlace(place: Place)

    fun onNoInternetConnectionWhenLoadingPhotos()
    fun onNoInternetConnectionWhenLoadingPlaceDetails()

    fun addPlaceToFavourites(place: Place)
}