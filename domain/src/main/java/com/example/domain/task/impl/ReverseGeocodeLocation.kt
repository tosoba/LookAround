package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.model.ReverseGeocodingData
import com.example.domain.task.base.SingleTaskWithInput
import com.example.domain.task.input.LocationInput
import io.reactivex.Single
import javax.inject.Inject

class ReverseGeocodeLocation @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<LocationInput, ReverseGeocodingData> {

    override fun execute(
        input: LocationInput
    ): Single<ReverseGeocodingData> = repository.reverseGeocodeLocation(
        latitude = input.latitude,
        longitude = input.longitude
    )
}