package com.achyzh.discovermeals2020.ui.meals_result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.business_logic.MealsFilter
import com.achyzh.discovermeals2020.repository.ISaver
import com.achyzh.discovermeals2020.repository.network.BackendAPI
import kotlinx.coroutines.*
import javax.inject.Inject

class MealsResultViewModel @Inject constructor(val saver: ISaver) : ViewModel() {
    var mealsLiveData : MutableLiveData<List<Meal>> = MutableLiveData()
    var progressLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var startLoad = true

    fun loadData(ingredients: Array<String>) {
        if (startLoad) {
            viewModelScope.launch {
                updProgress(true)
                withContext(Dispatchers.IO) {
                    var indx = 0
                    for (ingrName in ingredients) {
                        val meals = BackendAPI.filterAsync(ingrName).meals
                        withContext(Dispatchers.Default) {
                            val ingr = Ingredient(ingrName, null)
                            for (meal in meals) {
                                meal.addIngredient(ingr)
                            }
                            addMeals(meals.toMutableList())
                        }
                        indx += 1
                        val progress = indx < ingredients.size
                        updProgress(progress)
                        if (!progress)
                            startLoad = false
                        delay(50)
                    }
                }
            }
        }
    }

    fun getFavMeals() : Set<String> {
        return saver.getAllFavMealIds()
    }

    private fun updProgress(isOnProgress: Boolean) {
        progressLiveData.postValue(isOnProgress)
    }

    fun updMeals(meals: List<Meal>) {
        mealsLiveData!!.value = meals
    }

    suspend fun addMeals(meals: MutableList<Meal>) {
        val data: List<Meal>? = mealsLiveData.value
        if (data != null)
            meals.addAll(data)

        val filteredMeals = MealsFilter().filterMeals(meals)
        mealsLiveData.postValue(filteredMeals)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}