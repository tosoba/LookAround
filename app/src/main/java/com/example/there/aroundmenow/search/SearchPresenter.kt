package com.example.there.aroundmenow.search

import com.example.domain.task.impl.GetPlacesAutocompletePredictions
import com.example.there.aroundmenow.base.architecture.RxPresenter
import com.example.there.aroundmenow.util.ext.disposeWith
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val getPlacesAutocompletePredictions: GetPlacesAutocompletePredictions
) : RxPresenter<SearchState, SearchViewModel>() {

    fun searchForPlaces(query: String) {
        getPlacesAutocompletePredictions
            .run {
                if (viewModel.isPlacesAutocompleteDisposableInitialized)
                    cancelUnfinishedAndWithExecuteEventArgs(viewModel.placesAutocompleteDisposable, query, { it })
                else executeWithEventArgs(query, { it })
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { SearchState(it) }
            .subscribe(viewModel.state)
            .disposeWith(viewModel.disposables)
    }
}