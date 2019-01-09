package com.example.there.aroundmenow.placedetails

import com.example.there.aroundmenow.model.UIPlace
import com.example.there.aroundmenow.model.UISimplePlace

interface PlaceDetailsActions {
    fun findPlaceDetails(place: UISimplePlace)
    fun findPlacePhotos(id: String)
    fun setPlace(place: UIPlace)

    fun onNoInternetConnectionWhenLoadingPhotos()
    fun onNoInternetConnectionWhenLoadingPlaceDetails()

    fun addPlaceToFavourites(place: UIPlace, onSuccess: () -> Unit)
}