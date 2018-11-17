package com.example.there.aroundmenow.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.RxFragment
import com.example.there.aroundmenow.main.MainState
import java.util.concurrent.TimeUnit


class SearchFragment : RxFragment<MainState, SearchState, SearchViewModel, SearchPresenter>(
    SearchViewModel::class.java,
    MainState::class.java
) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun observeState() {
        uiDisposables += observableActivityState!!.map { it.placesQuery }
            .filter { it.length > 1 && it.isNotBlank() }
            .distinctUntilChanged()
            .debounce(1, TimeUnit.SECONDS)
            .subscribe { presenter.searchForPlaces(it) }

        uiDisposables += observableState.subscribe { Log.e("PLACES", it.places.joinToString { it.name }) }
    }
}
