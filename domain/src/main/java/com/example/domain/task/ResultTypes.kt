package com.example.domain.task

import android.graphics.Bitmap
import com.example.domain.repo.Result
import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.error.FindNearbyPlacesError
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.error.FindPlacePhotosError
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.google.android.gms.location.places.Place

typealias FindNearbyPlacesResult = Result<List<SimplePlace>, FindNearbyPlacesError>

typealias ReverseGeocodeLocationResult = Result<GeocodingInfo, ReverseGeocodeLocationError>

typealias FindPlaceDetailsResult = Result<Place, FindPlaceDetailsError>

typealias FindPlacePhotosResult = Result<List<Bitmap>, FindPlacePhotosError>