package com.holygunner.discover_meals.repository.network

import com.holygunner.discover_meals.models.Cuisine
import com.holygunner.discover_meals.tools.NetworkAccessibility
import retrofit2.await
import javax.inject.Inject

class BackendApiManager @Inject constructor(
    private val backendAPI: BackendAPI,
    private val networkAccessibility: NetworkAccessibility
){

    suspend fun filterAsync(ingr : String) : Cuisine {
        return if (networkAccessibility.isNetworkAvailable())
            backendAPI
                .filter(ingr)
                .await()
        else
            Cuisine()
    }

    suspend fun searchAsync(meal: String) : Cuisine? {
        return if (networkAccessibility.isNetworkAvailable())
            backendAPI
                .selectByName(meal)
                .await()
        else
            null
    }

    suspend fun requestMeal(mealId: Int) : Cuisine? {
        return if (networkAccessibility.isNetworkAvailable())
            backendAPI
                .selectById(mealId)
                .await()
        else
            null
    }
}