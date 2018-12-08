package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.model.SimplePOI
import com.example.domain.task.error.FindNearbyPOIsError
import com.example.there.aroundmenow.base.architecture.Data


data class POIsState(
    val pois: Data<List<SimplePOI>, FindNearbyPOIsError>
) {
    companion object {
        val INITIAL = POIsState(pois = Data.Value(emptyList()))
    }
}