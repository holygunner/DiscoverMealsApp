package com.achyzh.discovermeals2020.repository

import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.models.Meal

interface ISaver {
    fun addFavMeal(meal: Meal)
    fun removeMealFromFavs(meal: Meal)
    fun isMealFav(meal: Meal) : Boolean
    fun getAllFavMealIds() : Set<String>
    fun addSelectedIngredient(ingredient: Ingredient)
    fun removeSelectedIngredient(ingredient: Ingredient)
    fun getAllSelectedIngredients() : Set<String>
}