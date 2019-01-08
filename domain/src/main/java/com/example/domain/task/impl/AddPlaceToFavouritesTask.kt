package com.example.domain.task.impl

import com.example.domain.repo.IPlaceRepository
import com.example.domain.repo.model.SavedPlace
import com.example.domain.task.base.CompletableTaskWithInput
import io.reactivex.Completable
import javax.inject.Inject

class AddPlaceToFavouritesTask @Inject constructor(
    private val repository: IPlaceRepository
) : CompletableTaskWithInput<SavedPlace> {
    override fun executeWith(input: SavedPlace): Completable = repository.addPlaceToFavourites(input)
}