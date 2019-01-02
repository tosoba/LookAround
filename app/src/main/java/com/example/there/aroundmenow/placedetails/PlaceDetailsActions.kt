package com.example.there.aroundmenow.placedetails

import com.example.there.aroundmenow.model.UISimplePlace

interface PlaceDetailsActions {
    fun findPlaceDetails(place: UISimplePlace)
    fun findPlacePhotos(id: String)
}