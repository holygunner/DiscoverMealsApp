package com.holygunner.discover_meals.ui.selected_ingredients

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holygunner.discover_meals.business_logic.IngredientManager
import com.holygunner.discover_meals.models.Ingredient
import com.holygunner.discover_meals.models.UserSelection
import com.holygunner.discover_meals.repository.DbWrapper
import com.holygunner.discover_meals.repository.RepositoryComponent
import io.realm.RealmList
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectedIngredientsViewModel @Inject constructor(
    private val ingredientManager: IngredientManager,
    private val dbWrapper: DbWrapper
    ) : ViewModel() {
    val ingredientsLD = MutableLiveData<List<Ingredient>>()
    val itemsToDel = hashSetOf<Ingredient>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val userSelection = dbWrapper.getUserSelection()
            userSelection.load()
            if (!userSelection.isValid)
                return@launch

            val selectedIngredients = userSelection.selectedIngredients

            val selectedIngrs = mutableListOf<Ingredient>()
            for (name in selectedIngredients) {
                val category: String = ingredientManager.findIngredientCategory(name)
                val ingr = Ingredient(name = name, category = category)
                selectedIngrs.add(ingr)
            }

            postData(selectedIngrs)
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
            storeSelected(ingredients)
            postData(ingredients)
        }
    }

    private fun storeSelected(ingredients: List<Ingredient>) {
        viewModelScope.launch {
            val ingrs = RealmList<String>()
            ingrs.addAll(ingredients.map { it.name })
            val userSelection : UserSelection = UserSelection
                .Builder()
                .selectedIngredients(ingrs)
                .build();
            dbWrapper.storeUserSelection(userSelection)
        }
    }

}