package com.example.there.aroundmenow.places.pois

import com.example.domain.task.error.FindNearbyPOIsError
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.model.UISimplePlace


data class POIsState(
    val pois: ViewData<List<UISimplePlace>, FindNearbyPOIsError>
) {
    companion object {
        val INITIAL = POIsState(ViewData.Idle)
    }
}