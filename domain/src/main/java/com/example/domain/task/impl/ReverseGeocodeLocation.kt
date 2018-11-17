package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.model.ReverseGeocodingData
import com.example.domain.task.base.SingleTaskWithInput
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class ReverseGeocodeLocation @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<LatLng, ReverseGeocodingData> {
    override fun execute(input: LatLng): Single<ReverseGeocodingData> = repository.reverseGeocodeLocation(input)
}