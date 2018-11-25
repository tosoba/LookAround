package com.example.there.aroundmenow.di.app

import com.example.data.api.GeocodingAPIClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun geocodingAPIClientRetrofit(): Retrofit = Retrofit.Builder()
        .client(OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(GeocodingAPIClient.BASE_URL)
        .build()

    @Provides
    fun geocodingAPIClient(
        retrofit: Retrofit
    ): GeocodingAPIClient = retrofit.create(GeocodingAPIClient::class.java)
}