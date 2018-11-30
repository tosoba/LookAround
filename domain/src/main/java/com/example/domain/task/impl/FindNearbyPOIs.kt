package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.base.SingleTaskWithInput
import com.example.domain.task.result.FindNearbyPOIsResult
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class FindNearbyPOIs @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<LatLng, FindNearbyPOIsResult> {

    override fun executeWith(
        input: LatLng
    ): Single<FindNearbyPOIsResult> = repository.findNearbyPOIs(input)
}