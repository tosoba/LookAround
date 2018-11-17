package com.example.there.aroundmenow.search

import com.google.android.gms.location.places.Place

data class SearchState(val places: List<Place>) {
    companion object {
        val INITIAL = SearchState(emptyList())
    }
}