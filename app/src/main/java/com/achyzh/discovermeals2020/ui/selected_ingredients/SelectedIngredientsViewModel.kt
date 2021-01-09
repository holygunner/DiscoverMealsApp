package com.achyzh.discovermeals2020.ui.selected_ingredients

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.models.UserSelection
import com.achyzh.discovermeals2020.repository.DbWrapper
import com.achyzh.discovermeals2020.repository.RepositoryComponent
import io.realm.RealmList
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectedIngredientsViewModel @Inject constructor(
    private val repo: RepositoryComponent,
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
            postData(selectedIngredients)
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
            val ingrs = RealmList<Ingredient>()
            ingrs.addAll(ingredients)
            val userSelection : UserSelection = UserSelection
                .Builder()
                .selectedIngredients(ingrs)
                .build();
            dbWrapper.storeUserSelection(userSelection)
        }
    }

}