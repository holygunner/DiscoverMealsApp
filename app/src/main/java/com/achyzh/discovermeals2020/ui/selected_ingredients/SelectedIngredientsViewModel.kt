package com.achyzh.discovermeals2020.ui.selected_ingredients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.repository.RepositoryComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectedIngredientsViewModel @Inject constructor(
    private val repo: RepositoryComponent
    ) : ViewModel() {
    val ingredientsLD = MutableLiveData<List<Ingredient>>()
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    val itemsToDel = hashSetOf<Ingredient>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val data : MutableList<Ingredient> =
                repo.selectedIngredients
            postData(data)
        }
    }

    private fun postData(ingredients: List<Ingredient>) {
        ingredientsLD.postValue(ingredients)
    }

    fun deleteSelected() {
        val ingredients: MutableList<Ingredient> = mutableListOf()
        val value : List<Ingredient> = ingredientsLD.value!!
        ingredients.addAll(value)
        if (ingredients.isNotEmpty()) {
            for (ingr in itemsToDel) {
                ingredients.remove(ingr)
            }
            itemsToDel.clear()

            postData(ingredients)
            storeSelected(ingredients)
        }
    }

    private fun storeSelected(ingredients: List<Ingredient>) {
        repo.updSelectedIngredients(ingredients)
    }

}