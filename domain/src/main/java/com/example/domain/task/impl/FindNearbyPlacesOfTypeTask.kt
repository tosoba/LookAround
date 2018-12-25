package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.FindNearbyPlacesResult
import com.example.domain.task.base.SingleTaskWithInput
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class FindNearbyPlacesOfTypeTask @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<FindNearbyPlacesOfTypeTask.Input, FindNearbyPlacesResult> {

    override fun executeWith(
        input: Input
    ): Single<FindNearbyPlacesResult> = repository.findNearbyPlacesOfType(input.latLng, input.query)

    class Input(val latLng: LatLng, val query: String)
}