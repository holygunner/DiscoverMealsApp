package com.achyzh.discovermeals2020.ui.fav_meals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.repository.DbWrapper
import com.achyzh.discovermeals2020.repository.ISaver
import com.achyzh.discovermeals2020.repository.RepositoryComponent
import com.achyzh.discovermeals2020.repository.network.BackendAPI
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