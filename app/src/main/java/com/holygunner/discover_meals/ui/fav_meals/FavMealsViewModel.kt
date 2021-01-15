package com.holygunner.discover_meals.ui.fav_meals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holygunner.discover_meals.models.Meal
import com.holygunner.discover_meals.repository.DbWrapper
import com.holygunner.discover_meals.repository.ISaver
import com.holygunner.discover_meals.repository.RepositoryComponent
import com.holygunner.discover_meals.repository.network.BackendAPI
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavMealsViewModel @Inject constructor(
    val saver: ISaver,
    val dbWrapper: DbWrapper
    ) : ViewModel() {

    fun provideFavMealsLD() : LiveData<List<Meal>> {
        return dbWrapper.getFavMeals()
    }

    fun removeMealFromFavs(meal: Meal) {
        viewModelScope.launch {
            dbWrapper.copyOrUpdateMeal(meal, false)
        }
    }
}