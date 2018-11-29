package com.example.there.aroundmenow.di.app

import com.example.data.api.geocoding.GeocodingAPIClient
import com.example.data.api.overpass.OverpassAPIClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    private fun Retrofit.Builder.buildWithDefaultClientFactoriesAndBaseURL(
        url: String
    ): Retrofit = client(OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .build()

    @Provides
    @Named(GEOCODING_API_RETROFIT)
    fun geocodingAPIClientRetrofit(): Retrofit = Retrofit.Builder()
        .buildWithDefaultClientFactoriesAndBaseURL(GeocodingAPIClient.BASE_URL)

    @Provides
    fun geocodingAPIClient(
        @Named(GEOCODING_API_RETROFIT) retrofit: Retrofit
    ): GeocodingAPIClient = retrofit.create(GeocodingAPIClient::class.java)

    @Provides
    @Named(OVERPASS_API_RETROFIT)
    fun overpassAPIClientRetrofit(): Retrofit = Retrofit.Builder()
        .buildWithDefaultClientFactoriesAndBaseURL(OverpassAPIClient.BASE_URL)

    @Provides
    fun overpassAPIClient(
        @Named(OVERPASS_API_RETROFIT) retrofit: Retrofit
    ): OverpassAPIClient = retrofit.create(OverpassAPIClient::class.java)

    companion object {
        private const val GEOCODING_API_RETROFIT = "GEOCODING_API_RETROFIT"
        private const val OVERPASS_API_RETROFIT = "OVERPASS_API_RETROFIT"
    }
}