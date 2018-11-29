package com.example.data.api.overpass

import com.example.data.api.overpass.model.OverpassPlacesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassAPIClient {
    @GET
    fun getPlaces(@Query("data") query: String): Single<OverpassPlacesResponse>

    companion object {
        const val BASE_URL = "https://www.overpass-api.de/api/interpreter"
    }
}