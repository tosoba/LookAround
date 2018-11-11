package com.example.data.api

import com.example.data.Keys
import com.example.data.api.model.ReverseGeocodingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingAPIClient {

    @GET("json")
    fun reverseGeocode(
        @Query("latLng") latLng: String,
        @Query("key") key: String = Keys.GOOGLE_PLACES
    ): Single<ReverseGeocodingResponse>

    fun reverseGeocode(
        latitude: Double,
        longitude: Double,
        key: String = Keys.GOOGLE_PLACES
    ): Single<ReverseGeocodingResponse> = reverseGeocode(
        latLng = "$latitude,$longitude",
        key = key
    )

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/maps/api/geocode/"
    }
}