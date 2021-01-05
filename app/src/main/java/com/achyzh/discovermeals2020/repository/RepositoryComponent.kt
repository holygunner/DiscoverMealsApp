package com.achyzh.discovermeals2020.repository

import com.achyzh.discovermeals2020.business_logic.IngredientsComparator
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.repository.network.BackendAPI
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