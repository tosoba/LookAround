package com.example.domain.task

import com.example.domain.repo.Result
import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.error.FindNearbyPOIsError
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.google.android.gms.location.places.Place

typealias FindNearbyPOIsResult = Result<List<SimplePlace>, FindNearbyPOIsError>

typealias ReverseGeocodeLocationResult = Result<GeocodingInfo, ReverseGeocodeLocationError>

typealias FindPlaceDetailsResult = Result<Place, FindPlaceDetailsError>