package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.FindNearbyPlacesResult
import com.example.domain.task.base.SingleTaskWithInput
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class FindNearbyPOIsTask @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<LatLng, FindNearbyPlacesResult> {

    override fun executeWith(
        input: LatLng
    ): Single<FindNearbyPlacesResult> = repository.findNearbyPOIs(input)
}

