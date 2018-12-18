package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.model.SimplePOI
import com.example.domain.task.error.FindNearbyPOIsError
import com.example.there.aroundmenow.base.architecture.view.ViewData


data class POIsState(
    val pois: ViewData<List<SimplePOI>, FindNearbyPOIsError>
) {
    companion object {
        val INITIAL = POIsState(pois = ViewData.Idle)
    }
}