package com.example.data.api.geocoding

import com.example.data.api.geocoding.model.ReverseGeocodingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeocodingAPIClient {
    @GET("{latLng}")
    fun reverseGeocode(
        @Path(value = "latLng") latLng: String,
        @Query(value = "geoit") responseType: String = JSON_RESPONSE_TYPE
    ): Single<ReverseGeocodingResponse>

    companion object {
        const val BASE_URL = "https://geocode.xyz/"

        private const val JSON_RESPONSE_TYPE = "json"
    }
}