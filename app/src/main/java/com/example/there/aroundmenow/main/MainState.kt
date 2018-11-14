package com.example.there.aroundmenow.main

data class MainState(
    val placesQuery: String
) {
    companion object {
        val INITIAL = MainState("")
    }
}