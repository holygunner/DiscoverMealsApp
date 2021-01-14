package com.holygunner.discovermeals.ui.search

import androidx.lifecycle.*
import com.holygunner.discovermeals.models.Meal
import com.holygunner.discovermeals.repository.DbWrapper
import com.holygunner.discovermeals.repository.network.BackendAPI
import com.holygunner.discovermeals.repository.network.BackendApiManager
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    val dbWrapper: DbWrapper,
    private val backendApiManager: BackendApiManager
) : ViewModel() {
    var mealsLiveData : MutableLiveData<List<Meal>> = MutableLiveData()
    var favsMeals: MutableLiveData<Set<Meal>> = MutableLiveData()
    private val viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun searchData(query: String) {
        viewModelScope.launch {
            Timber.d("invoke search - $query")
            val meals = backendApiManager
                .searchAsync(query)
                ?.meals
            if (meals != null) {
                val mealList = meals.toMutableList()
                checkFavMeals(mealList)
                mealsLiveData.postValue(mealList)
            }
        }
    }

//    val favs = liveData {
//        val data = dbWrapper.getFavMealsAsync()
//        emit(data)
//    }

    var favs: List<Meal> = mutableListOf()

    init {
        viewModelScope.launch {
            favs = dbWrapper.getFavMealsAsync()
        }
    }

    private fun checkFavMeals(mealList: List<Meal>) {
        for (meal in mealList) {
            for (fav in favs) {
                meal.isFav = fav.name == meal.name
            }
        }
    }

    fun provideFavsLD() : LiveData<List<Meal>> {
        return dbWrapper.getFavMealsLD()
    }

    fun resetSearch() {
        mealsLiveData.postValue(listOf())
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}