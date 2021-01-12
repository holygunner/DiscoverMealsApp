package com.achyzh.discovermeals2020.repository.network

import com.achyzh.discovermeals2020.models.Cuisine
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BackendAPI {
    @GET("api/json/v1/23543/filter.php")
    fun filter(@Query("i") ingr : String) : Call<Cuisine>

    @GET("api/json/v1/23543/search.php")
    fun selectByName(@Query("s") name : String) : Call<Cuisine>

    @GET("api/json/v1/23543/lookup.php")
    fun selectById(@Query("i") id : Int) : Call<Cuisine>

}