package com.example.there.aroundmenow.main

import com.example.there.aroundmenow.base.architecture.RxPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor() : RxPresenter<MainState, MainViewModel>() {
    fun updatePlacesQuery(query: String) {
        mutate { it.copy(placesQuery = query) }
    }
}