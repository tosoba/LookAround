package com.example.domain.task

import android.graphics.Bitmap
import com.example.domain.repo.Result
import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.repo.model.SavedPlace
import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.error.*
import com.google.android.gms.location.places.Place

typealias FindNearbyPlacesResult = Result<List<SimplePlace>, FindNearbyPlacesError>

typealias ReverseGeocodeLocationResult = Result<GeocodingInfo, ReverseGeocodeLocationError>

typealias FindPlaceDetailsResult = Result<Place, FindPlaceDetailsError>

typealias FindPlacePhotosResult = Result<List<Bitmap>, FindPlacePhotosError>

typealias GetFavouritePlacesResult = Result<List<SavedPlace>, GetFavouritePlacesError>