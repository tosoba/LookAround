package com.example.there.aroundmenow.search

import com.example.domain.task.impl.GetPlacesAutocompletePredictions
import com.example.there.aroundmenow.base.architecture.RxPresenter
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val getPlacesAutocompletePredictions: GetPlacesAutocompletePredictions
) : RxPresenter<SearchState, SearchViewModel>() {

    fun searchForPlaces(query: String) {
        getPlacesAutocompletePredictions
            .cancelUnfinishedAndExecuteEventArgs(viewModel.placesAutocompleteDisposable, query, { it })
            .mapToStateThenSubscribeAndDisposeWithViewModel({ _, places -> SearchState(places) })
    }
}