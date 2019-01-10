package com.example.there.aroundmenow.di.app

import com.example.data.api.overpass.OverpassAPIClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
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
    @Named(OVERPASS_API_RETROFIT)
    fun overpassAPIClientRetrofit(): Retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().addInterceptor {
            val urlStr = it.request().url().toString()
                .replace("%25", "%")
                .replace("%3D", "=")
                .replace("%5B", "[")
                .replace("%5D", "]")
                .replace("%3A", ":")
                .replace("%3B", ";")
                .replace("%28", "(")
                .replace("%29", ")")
                .replace("%2C", ",")
            val newRequest = Request.Builder()
                .url(urlStr)
                .build()
            it.proceed(newRequest)
        }.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(OverpassAPIClient.BASE_URL)
        .build()

    @Provides
    fun overpassAPIClient(
        @Named(OVERPASS_API_RETROFIT) retrofit: Retrofit
    ): OverpassAPIClient = retrofit.create(OverpassAPIClient::class.java)

    companion object {
        private const val OVERPASS_API_RETROFIT = "OVERPASS_API_RETROFIT"
    }
}