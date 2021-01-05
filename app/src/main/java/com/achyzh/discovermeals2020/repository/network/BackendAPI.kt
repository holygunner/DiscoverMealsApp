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

    companion object Factory {
        val retrofit = create()

        fun create() : BackendAPI {
            val baseUrl = "https://www.themealdb.com/"
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(BackendAPI::class.java)
        }

        suspend fun filterAsync(ingr : String) : Cuisine {
            val result = retrofit
                .filter(ingr)
                .await()
            return result
        }

        suspend fun searchAsync(meal: String) : Cuisine {
            return retrofit
                .selectByName(meal)
                .await()
        }

        suspend fun requestMeal(mealId: Int) : Cuisine {
            return retrofit
                .selectById(mealId)
                .await()
        }
    }
}