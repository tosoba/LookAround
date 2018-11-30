package com.example.domain.repo

import com.example.domain.task.result.FindNearbyPOIsResult
import com.example.domain.task.result.ReverseGeocodeLocationResult
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface IPlaceRepository {
    fun reverseGeocodeLocation(latLng: LatLng): Single<ReverseGeocodeLocationResult>
    fun findNearbyPOIs(latLng: LatLng): Single<FindNearbyPOIsResult>
}