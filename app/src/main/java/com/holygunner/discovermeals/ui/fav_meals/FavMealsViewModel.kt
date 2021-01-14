package com.holygunner.discovermeals.ui.fav_meals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holygunner.discovermeals.models.Meal
import com.holygunner.discovermeals.repository.DbWrapper
import com.holygunner.discovermeals.repository.ISaver
import com.holygunner.discovermeals.repository.RepositoryComponent
import com.holygunner.discovermeals.repository.network.BackendAPI
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