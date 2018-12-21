package com.example.domain.repo

import com.example.domain.repo.model.GeocodingInfo
import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.error.FindNearbyPOIsError
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface IPlaceRepository {
    fun reverseGeocodeLocation(latLng: LatLng): Single<Result<GeocodingInfo, ReverseGeocodeLocationError>>
    fun findNearbyPOIs(latLng: LatLng): Single<Result<List<SimplePlace>, FindNearbyPOIsError>>
}