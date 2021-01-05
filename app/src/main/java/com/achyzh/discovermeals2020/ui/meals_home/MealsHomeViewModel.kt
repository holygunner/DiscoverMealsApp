package com.achyzh.discovermeals2020.ui.meals_home

import androidx.lifecycle.*
import com.achyzh.discovermeals2020.business_logic.IngredientManagerKt
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.models.IngredientsCategory
import com.achyzh.discovermeals2020.repository.RepositoryComponent
import kotlinx.coroutines.*
import javax.inject.Inject

class MealsHomeViewModel @Inject constructor(
    private val repo: RepositoryComponent,
    private val ingredientManager: IngredientManagerKt
    ) : ViewModel() {
    private val categoriesLiveData = MutableLiveData<List<IngredientsCategory>>()
    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val computationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        loadFromAssets()
    }

    private fun loadFromAssets() {
        ioScope.launch {
            postCategories(ingredientManager.getAllIngredientCategories()!!)
        }
    }

    fun refreshFilledIngredients() {
        val selectedIngredients = repo.selectedIngredients
        val categories: List<IngredientsCategory>? = categoriesLiveData.value
        for (category in categories!!) {
            for (ingr in category.ingredients) {
                ingr.isFill = selectedIngredients.contains(ingr)
            }
        }
    }

    fun observeCategoriesLiveData() : LiveData<List<IngredientsCategory>> {
        return categoriesLiveData.map {
            refreshFilledIngredients()
            it
        }
    }

    private fun postCategories(categories: List<IngredientsCategory>) {
        categoriesLiveData.postValue(categories)
    }

    fun storeSelectedIngredients(categories: MutableList<IngredientsCategory>) {
        ioScope.launch {
            val toSave = mutableListOf<Ingredient>()
            for (category in categories) {
                val filtered = category.ingredients.filter { it.isFill }
                toSave.addAll(filtered)
            }
            repo.updSelectedIngredients(toSave)
        }
    }
}