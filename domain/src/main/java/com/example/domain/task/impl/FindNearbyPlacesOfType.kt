package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.base.SingleTaskWithInput
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class FindNearbyPlacesOfType @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<FindNearbyPlacesOfType.Input, List<Place>> {

    override fun executeWith(
        input: Input
    ): Single<List<Place>> = repository.getNearbyPlacesOfType(
        latLng = input.latLng,
        type = input.type
    )

    class Input(
        val latLng: LatLng,
        val type: String
    )
}