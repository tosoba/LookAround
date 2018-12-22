package com.example.domain.repo

import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.FindNearbyPOIsResult
import com.example.domain.task.FindPlaceDetailsResult
import com.example.domain.task.ReverseGeocodeLocationResult
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface IPlaceRepository {
    fun reverseGeocodeLocation(latLng: LatLng): Single<ReverseGeocodeLocationResult>
    fun findNearbyPOIs(latLng: LatLng): Single<FindNearbyPOIsResult>
    fun findPlaceDetails(simplePlace: SimplePlace): Single<FindPlaceDetailsResult>
}