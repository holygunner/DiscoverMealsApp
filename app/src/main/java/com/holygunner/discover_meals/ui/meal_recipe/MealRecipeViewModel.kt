package com.holygunner.discover_meals.ui.meal_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holygunner.discover_meals.business_logic.RecipeFactory
import com.holygunner.discover_meals.models.Cuisine
import com.holygunner.discover_meals.models.Meal
import com.holygunner.discover_meals.repository.DbWrapper
import com.holygunner.discover_meals.repository.ISaver
import com.holygunner.discover_meals.repository.network.BackendAPI
import com.holygunner.discover_meals.repository.network.BackendApiManager
import kotlinx.coroutines.*
import javax.inject.Inject


class MealRecipeViewModel @Inject constructor(
    val saver: ISaver,
    val dbWrapper: DbWrapper,
    private val backendApiManager: BackendApiManager
) : ViewModel() {
    private var isFav : Boolean = false
    private var mealId : Int = 0

    fun setMealId(mealId : Int) {
        this.mealId = mealId
    }

    fun loadData(meal: Meal) {
        viewModelScope.launch {
            isFav = meal.isFav
            val storedMeal = dbWrapper.getMealAsync(meal.id)
            storedMeal.load()
            if (storedMeal.isValid) {
                val recipe = RecipeFactory.build(storedMeal)
                if (recipe.isEmpty() || !storedMeal.isFetchedCompletely || storedMeal.instruction.isNullOrEmpty()) {
                    requestMealAsync(meal.id)
                }
            } else {
                requestMealAsync(meal.id)
            }
        }
    }

    fun provideMealsLD() : LiveData<List<Meal>> {
        return dbWrapper.getMealLD(mealId)
    }

    fun invertMealFav() {
        viewModelScope.launch {
            isFav = !isFav
            dbWrapper.copyOrUpdateMeal(mealId, isFav)
        }
    }

    private fun requestMealAsync(mealId: Int) {
        viewModelScope.launch {
            var meal: Meal? = null
            withContext(Dispatchers.IO) {
                val cuisine: Cuisine? = backendApiManager.requestMeal(mealId)
                cuisine?.let {
                    meal = cuisine.meals[0]
                    meal!!.isFetchedCompletely = true
                    meal!!.isFav = this@MealRecipeViewModel.isFav
                }
            }
            meal?.let { storeFetchedMeal(it) }
        }
    }

    private suspend fun storeFetchedMeal(meal: Meal) {
        dbWrapper.copyOrUpdateMeal2(meal)
    }
}