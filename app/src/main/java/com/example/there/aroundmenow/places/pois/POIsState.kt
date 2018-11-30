package com.example.there.aroundmenow.places.pois

import com.example.domain.repo.model.SimplePOI


data class POIsState(
    val pois: List<SimplePOI>
) {
    companion object {
        val INITIAL = POIsState(emptyList())
    }
}