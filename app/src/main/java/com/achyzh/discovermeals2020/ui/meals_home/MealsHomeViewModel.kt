package com.achyzh.discovermeals2020.ui.meals_home

import androidx.lifecycle.*
import com.achyzh.discovermeals2020.business_logic.IngredientManager
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.models.IngredientsCategory
import com.achyzh.discovermeals2020.models.UserSelection
import com.achyzh.discovermeals2020.repository.DbWrapper
import com.achyzh.discovermeals2020.repository.RepositoryComponent
import io.realm.RealmList
import kotlinx.coroutines.*
import javax.inject.Inject

class MealsHomeViewModel @Inject constructor(
    private val repo: RepositoryComponent,
    private val dbWrapper: DbWrapper,
    private val ingredientManager: IngredientManager
    ) : ViewModel() {
    private val categoriesLiveData = MutableLiveData<List<IngredientsCategory>>()
    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val userSelection = UserSelection.Builder()
        .selectedIngredients(RealmList())
        .build()

    init {
        loadFromAssets()
    }

    private fun loadFromAssets() {
        ioScope.launch {
            postCategories(ingredientManager.getAllIngredientCategories()!!)
        }
    }

    fun refreshFilledIngredients() {
        viewModelScope.launch {
            val userSelection = dbWrapper.getUserSelection()
            userSelection.load()
            if (!userSelection.isValid)
                return@launch

            this@MealsHomeViewModel.userSelection.refreshContent(userSelection.selectedIngredients)

            val categories: List<IngredientsCategory>? = categoriesLiveData.value
            for (category in categories!!) {
                for (ingr in category.ingredients) {
                    ingr.isFill = userSelection.selectedIngredients.contains(ingr.name)
                }
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

    fun getSelectedIngredients() : Array<Ingredient> {
        return if (userSelection.isValid) {
            val selectedIngs = mutableListOf<Ingredient>()
            val categories: List<IngredientsCategory>? = categoriesLiveData.value
            for (category in categories!!) {
                for (ingr in category.ingredients) {
                    val isFill = userSelection.selectedIngredients.contains(ingr.name)
                    if (isFill)
                        selectedIngs.add(ingr)
                }
            }
            return selectedIngs.toTypedArray()
        } else
            arrayOf()
    }

    fun storeSelectedIngredients(categories: MutableList<IngredientsCategory>) {
        viewModelScope.launch {
            val toSave = RealmList<String>()
            for (category in categories) {
                val filtered = category.ingredients
                    .filter { it.isFill }
                    .map { it.name }
                toSave.addAll(filtered)
            }
            if (userSelection.isValid)
                userSelection.refreshContent(toSave)
            dbWrapper.storeUserSelection(userSelection)
        }
    }
}