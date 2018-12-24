package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.model.SimplePlace
import com.example.domain.task.FindPlaceDetailsResult
import com.example.domain.task.base.SingleTaskWithInput
import io.reactivex.Single
import javax.inject.Inject

class FindPlaceDetailsTask @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<SimplePlace, FindPlaceDetailsResult> {

    override fun executeWith(
        input: SimplePlace
    ): Single<FindPlaceDetailsResult> = repository.findPlaceDetails(input)
}