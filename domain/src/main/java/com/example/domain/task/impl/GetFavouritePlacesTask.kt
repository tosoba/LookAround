package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.task.GetFavouritePlacesResult
import com.example.domain.task.base.FlowableTask
import io.reactivex.Flowable
import javax.inject.Inject

class GetFavouritePlacesTask @Inject constructor(
    private val repository: IPlaceRepository
) : FlowableTask<GetFavouritePlacesResult> {

    override val result: Flowable<GetFavouritePlacesResult>
        get() = repository.getFavouritePlaces()
}