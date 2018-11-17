package com.example.there.aroundmenow.search

import com.example.there.aroundmenow.base.architecture.RxViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SearchViewModel @Inject constructor() : RxViewModel<SearchState>(SearchState.INITIAL) {
    lateinit var placesAutocompleteDisposable: Disposable
}