package com.achyzh.discovermeals2020.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.repository.network.BackendAPI
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {
    var mealsLiveData : MutableLiveData<List<Meal>> = MutableLiveData()
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    val backendAPI = BackendAPI

    fun searchData(query: String) {
        viewModelScope.launch {
            Log.d("TAG2", "invoke search - $query")
            val meals = backendAPI
                .searchAsync(query)
                .meals
            if (meals != null) {
                val mealList = meals.toList()
                mealsLiveData.postValue(mealList)
            }
        }
    }

    fun resetSearch() {
        mealsLiveData.postValue(listOf())
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}