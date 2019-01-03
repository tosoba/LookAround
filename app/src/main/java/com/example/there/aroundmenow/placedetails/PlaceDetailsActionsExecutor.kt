package com.example.there.aroundmenow.placedetails

import com.example.domain.repo.Result
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.error.FindPlacePhotosError
import com.example.domain.task.impl.FindPlaceDetailsTask
import com.example.domain.task.impl.FindPlacePhotosTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.model.UISimplePlace
import com.google.android.gms.location.places.Place
import javax.inject.Inject

class PlaceDetailsActionsExecutor @Inject constructor(
    viewModel: PlaceDetailsViewModel,
    private val findPlaceDetailsTask: FindPlaceDetailsTask,
    private val findPlacePhotosTask: FindPlacePhotosTask
) : RxActionsExecutor.HostUnaware<PlaceDetailsState, PlaceDetailsViewModel>(viewModel), PlaceDetailsActions {

    override fun findPlaceDetails(place: UISimplePlace) {
        mutateState { it.copy(place = ViewDataState.Loading) }

        findPlaceDetailsTask.executeWithInput(
            input = place.domain,
            onErrorReturn = { Result.Error(FindPlaceDetailsError.Exception(it)) }
        ).mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, result ->
            when (result) {
                is Result.Value -> lastState.copy(
                    place = ViewDataState.Value(result.value)
                )
                is Result.Error -> lastState.copy(
                    place = ViewDataState.Error(result.error)
                )
            }
        })
    }

    override fun findPlacePhotos(id: String) {
        mutateState { it.copy(photos = ViewDataState.Loading) }

        findPlacePhotosTask.executeWithInput(
            input = id,
            onErrorReturn = { Result.Error(FindPlacePhotosError.Exception(it)) }
        ).mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, photosResult ->
            when (photosResult) {
                is Result.Value -> lastState.copy(
                    photos = ViewDataState.Value(photosResult.value)
                )
                is Result.Error -> lastState.copy(
                    photos = ViewDataState.Error(photosResult.error)
                )
            }
        })
    }

    override fun setPlace(place: Place) {
        mutateState { it.copy(place = ViewDataState.Value(place)) }
    }
}