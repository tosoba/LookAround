package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.model.NearbyPOIsData
import com.example.domain.task.base.SingleTaskWithInput
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class FindNearbyPOIs @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<LatLng, NearbyPOIsData> {
    override fun executeWith(input: LatLng): Single<NearbyPOIsData> = repository.getNearbyPOIs(input)
}