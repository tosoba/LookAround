package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.base.SingleTaskWithInput
import com.google.android.gms.location.places.Place
import io.reactivex.Single
import javax.inject.Inject

class GetPlacesAutocompletePredictions @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<String, List<Place>> {
    override fun executeWith(input: String): Single<List<Place>> = repository.getPlacesAutocompletePredictions(input)
}