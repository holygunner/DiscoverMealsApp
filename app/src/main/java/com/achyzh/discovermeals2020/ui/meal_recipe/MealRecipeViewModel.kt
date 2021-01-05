package com.achyzh.discovermeals2020.ui.meal_recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.business_logic.RecipeFactory
import com.achyzh.discovermeals2020.repository.ISaver
import com.achyzh.discovermeals2020.repository.network.BackendAPI
import kotlinx.coroutines.*
import javax.inject.Inject

class MealRecipeViewModel @Inject constructor(
    val saver: ISaver
    ) : ViewModel() {
    val mealLD = MutableLiveData<Meal>()
    val favLD = MutableLiveData<Boolean>()
    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun loadData(meal: Meal) {
        val recipe = RecipeFactory.build(meal)
        if (recipe.isEmpty()) {
            requestMealAsync(meal.id)
        }
        postMeal(meal)
    }

    fun requestIsMealFav(meal: Meal) {
        viewModelScope.launch {
            val isFav = saver.isMealFav(meal)
            postMealFav(isFav)
        }
    }

    fun invertMealFav() {
        viewModelScope.launch {
            val isFav = !(favLD.value as Boolean)
            storeMealFav(isFav)
            postMealFav(isFav)
        }
    }

    private fun storeMealFav(isFav: Boolean) {
        val meal = mealLD.value
        if (isFav)
            saver.addFavMeal(meal!!)
        else
            saver.removeMealFromFavs(meal!!)

//        App.
//        dbWrapper
//            .storeMeal(meal)

//        App.dbWrapper.storeMeal(meal)
    }

    private fun requestMealAsync(mealId: Int) {
        viewModelScope.launch {
            val cuisine = BackendAPI.requestMeal(mealId)
            val meal = cuisine.meals[0]
            postMeal(meal)
        }
    }

    private fun postMealFav(isFav: Boolean) {
        favLD.postValue(isFav)
    }

    private fun postMeal(meal: Meal) {
        mealLD.postValue(meal)
    }
}