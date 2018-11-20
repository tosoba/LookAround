package com.example.there.aroundmenow.places.pois

import com.example.domain.task.impl.FindNearbyPOIs
import com.example.there.aroundmenow.base.architecture.RxPresenter
import javax.inject.Inject

class POIsPresenter @Inject constructor(
    private val findNearbyPOIs: FindNearbyPOIs
) : RxPresenter<POIsState, POIsViewModel>() {

}