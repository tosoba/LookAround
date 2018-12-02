package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.ReverseGeocodeLocationResult
import com.example.domain.task.base.SingleTaskWithInput
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class ReverseGeocodeLocationTask @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<LatLng, ReverseGeocodeLocationResult> {

    override fun executeWith(
        input: LatLng
    ): Single<ReverseGeocodeLocationResult> = repository.reverseGeocodeLocation(input)
}