package com.example.there.lookaround.placedetails

import com.example.domain.repo.Result
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.error.FindPlacePhotosError
import com.example.domain.task.impl.AddPlaceToFavouritesTask
import com.example.domain.task.impl.FindPlaceDetailsTask
import com.example.domain.task.impl.FindPlacePhotosTask
import com.example.there.lookaround.base.architecture.executor.RxActionsExecutor
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.model.UIPlace
import com.example.there.lookaround.model.UISimplePlace
import com.example.there.lookaround.util.ext.ui
import io.reactivex.functions.Action
import javax.inject.Inject


class PlaceDetailsActionsExecutor @Inject constructor(
    viewModel: PlaceDetailsViewModel,
    private val findPlaceDetailsTask: FindPlaceDetailsTask,
    private val findPlacePhotosTask: FindPlacePhotosTask,
    private val addPlaceToFavouritesTask: AddPlaceToFavouritesTask
) : RxActionsExecutor.HostUnaware<PlaceDetailsState, PlaceDetailsViewModel>(viewModel), PlaceDetailsActions {

    override fun findPlaceDetails(place: UISimplePlace) {
        mutateState { it.copy(place = ViewDataState.Loading) }

        findPlaceDetailsTask.executeWithInput(
            input = place.domain,
            onErrorReturn = { Result.Error(FindPlaceDetailsError.Exception(it)) }
        ).mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, result ->
            when (result) {
                is Result.Value -> lastState.copy(
                    place = ViewDataState.Value(result.value.ui)
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

    override fun setPlace(place: UIPlace) = mutateState { it.copy(place = ViewDataState.Value(place)) }

    override fun onNoInternetConnectionWhenLoadingPhotos() = mutateState {
        it.copy(photos = ViewDataState.Error(FindPlacePhotosError.NoInternetConnection))
    }

    override fun onNoInternetConnectionWhenLoadingPlaceDetails() = mutateState {
        it.copy(place = ViewDataState.Error(FindPlaceDetailsError.NoInternetConnection))
    }

    override fun addPlaceToFavourites(place: UIPlace, onSuccess: () -> Unit) {
        addPlaceToFavouritesTask.executeWithInput(input = place.domain)
            .subscribeAndDisposeWithViewModel(onComplete = Action(onSuccess))
    }
}