package com.example.there.lookaround.placedetails

import com.example.there.lookaround.model.UIPlace
import com.example.there.lookaround.model.UISimplePlace

interface PlaceDetailsActions {
    fun findPlaceDetails(place: UISimplePlace)
    fun findPlacePhotos(id: String)
    fun setPlace(place: UIPlace)

    fun onNoInternetConnectionWhenLoadingPhotos()
    fun onNoInternetConnectionWhenLoadingPlaceDetails()

    fun addPlaceToFavourites(place: UIPlace, onSuccess: () -> Unit)
}