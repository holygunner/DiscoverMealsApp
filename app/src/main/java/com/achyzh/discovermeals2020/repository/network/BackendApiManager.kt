package com.achyzh.discovermeals2020.repository.network

import com.achyzh.discovermeals2020.models.Cuisine
import retrofit2.await
import javax.inject.Inject

class BackendApiManager @Inject constructor(
    private val backendAPI: BackendAPI
){

    suspend fun filterAsync(ingr : String) : Cuisine {
        return backendAPI
            .filter(ingr)
            .await()
    }

    suspend fun searchAsync(meal: String) : Cuisine {
        return backendAPI
            .selectByName(meal)
            .await()
    }

    suspend fun requestMeal(mealId: Int) : Cuisine {
        return backendAPI
            .selectById(mealId)
            .await()
    }
}