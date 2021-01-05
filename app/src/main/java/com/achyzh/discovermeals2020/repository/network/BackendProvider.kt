package com.achyzh.discovermeals2020.repository.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BackendProvider {
    lateinit var retrofit: Retrofit
    lateinit var backendAPI: BackendAPI

    fun init(baseUrl: String ) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun provideBackendApi() : BackendAPI {
        if (backendAPI == null) {
            backendAPI = retrofit.create(BackendAPI::class.java)
        }
        return backendAPI
    }
}