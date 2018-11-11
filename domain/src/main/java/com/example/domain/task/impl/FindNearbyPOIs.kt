package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.task.base.SingleTaskWithInput
import com.example.domain.task.input.LocationInput
import io.reactivex.Single
import javax.inject.Inject

class FindNearbyPOIs @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<LocationInput, NearbyPOIsData> {

    override fun execute(input: LocationInput): Single<NearbyPOIsData> = repository.getNearbyPOIs(
        latitude = input.latitude,
        longitude = input.longitude
    )
}