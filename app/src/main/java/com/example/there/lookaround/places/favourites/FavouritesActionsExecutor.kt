package com.example.there.lookaround.places.favourites

import com.example.domain.repo.Result
import com.example.domain.task.error.GetFavouritePlacesError
import com.example.domain.task.impl.GetFavouritePlacesTask
import com.example.there.lookaround.base.architecture.executor.RxActionsExecutor
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.list.simpleplaces.SimplePlacesListEvent
import com.example.there.lookaround.model.UIPlace
import javax.inject.Inject

class FavouritesActionsExecutor @Inject constructor(
    viewModel: FavouritesViewModel,
    private val getFavouritePlacesTask: GetFavouritePlacesTask
) : RxActionsExecutor.HostUnaware<FavouritesState, FavouritesViewModel>(viewModel), FavouritesActions {

    override fun getFavouritesPlaces() {
        getFavouritePlacesTask.execute(
            onErrorReturn = { Result.Error(GetFavouritePlacesError(it)) }
        ).mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, result ->
            when (result) {
                is Result.Value -> lastState.copy(places = ViewDataState.Value(result.value.map {
                    UIPlace.fromDomain(it)
                }))
                is Result.Error -> lastState.copy(places = ViewDataState.Error(result.error))
            }
        })
    }

    override fun onListEvent(event: SimplePlacesListEvent) = mutateState {
        it.copy(listEvent = ViewDataState.Value(event))
    }

    override fun onListEventHandled() = mutateState {
        it.copy(listEvent = ViewDataState.Idle)
    }
}