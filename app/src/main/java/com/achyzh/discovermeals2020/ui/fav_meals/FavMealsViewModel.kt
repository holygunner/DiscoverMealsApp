package com.achyzh.discovermeals2020.ui.fav_meals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.repository.ISaver
import com.achyzh.discovermeals2020.repository.RepositoryComponent
import com.achyzh.discovermeals2020.repository.network.BackendAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavMealsViewModel @Inject constructor(
    val saver: ISaver,
    val repo: RepositoryComponent
    ) : ViewModel() {
    var favMealsLD : MutableLiveData<MutableList<Meal>> = MutableLiveData()
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    private var toLoad = true

    init {
        loadData()
    }

    private fun loadData() {
        if (toLoad) {
            viewModelScope.launch {
                val favMealIds = saver.getAllFavMealIds()
                if (favMealIds.isNotEmpty()) {
                    val api = BackendAPI
                    // iterate and load an each meal
                    var indx = 0
                    for (mealId in favMealIds) {
                        indx += 1
                        val meal = api.requestMeal(mealId.toInt()).meals[0]
                        postMeal(meal)
                        if (indx == favMealIds.size - 1) {
                            toLoad = false
                        }
                    }
                }
            }
        }
    }


    fun removeMealFromFavs(meal: Meal) {
        viewModelScope.launch {
            saver.removeMealFromFavs(meal)
            val meals: MutableList<Meal>? = favMealsLD.value
            meals!!.remove(meal)
            favMealsLD.postValue(meals)
        }
    }

    private fun postMeal(meal: Meal) {
        var meals: MutableList<Meal>? = favMealsLD.value
        if (meals == null)
            meals = mutableListOf()
        meals.add(meal)
        favMealsLD.postValue(meals)

    }
}