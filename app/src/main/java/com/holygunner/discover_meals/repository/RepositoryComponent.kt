package com.holygunner.discover_meals.repository

import com.holygunner.discover_meals.business_logic.IngredientsComparator
import com.holygunner.discover_meals.models.Ingredient
import com.holygunner.discover_meals.repository.network.BackendAPI
import javax.inject.Inject

class RepositoryComponent
@Inject constructor (
    val backendAPI: BackendAPI,
    val saver: ISaver) {

    var selectedIngredients : MutableList<Ingredient> = mutableListOf()

    fun updSelectedIngredients(selectedIngredients: List<Ingredient>) {
        this.selectedIngredients.clear()
        val sorted = selectedIngredients.sortedWith(IngredientsComparator())
        this.selectedIngredients.addAll(sorted)
    }
}