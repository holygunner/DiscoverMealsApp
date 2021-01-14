package com.holygunner.discovermeals.repository

import com.holygunner.discovermeals.business_logic.IngredientsComparator
import com.holygunner.discovermeals.models.Ingredient
import com.holygunner.discovermeals.repository.network.BackendAPI
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