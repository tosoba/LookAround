package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.FindPlacePhotosResult
import com.example.domain.task.base.SingleTaskWithInput
import io.reactivex.Single
import javax.inject.Inject

class FindPlacePhotosTask @Inject constructor(
    private val repository: IPlaceRepository
) : SingleTaskWithInput<String, FindPlacePhotosResult> {

    override fun executeWith(
        input: String
    ): Single<FindPlacePhotosResult> = repository.findPlacePhotos(id = input)
}