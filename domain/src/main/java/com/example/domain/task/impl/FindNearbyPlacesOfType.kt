package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.base.SingleTaskWithInput
import com.example.domain.task.input.LocationInput
import io.reactivex.Single
import se.walkercrou.places.Place
import javax.inject.Inject

class FindNearbyPlacesOfType @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<FindNearbyPlacesOfType.Input, List<Place>> {

    override fun execute(
        input: Input
    ): Single<List<Place>> = repository.getNearbyPlacesOfType(
        latitude = input.latitude,
        longitude = input.longitude,
        type = input.type
    )

    class Input(
        latitude: Double,
        longitude: Double,
        val type: String
    ) : LocationInput(latitude, longitude)
}