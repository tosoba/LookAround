package com.example.there.aroundmenow.places.pois

import com.example.domain.task.error.FindNearbyPOIsError
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.model.UISimplePOI


data class POIsState(
    val pois: ViewData<List<UISimplePOI>, FindNearbyPOIsError>
) {
    companion object {
        val INITIAL = POIsState(ViewData.Idle)
    }
}