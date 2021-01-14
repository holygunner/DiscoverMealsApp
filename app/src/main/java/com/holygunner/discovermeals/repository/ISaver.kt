package com.holygunner.discovermeals.repository

import com.holygunner.discovermeals.models.Ingredient
import com.holygunner.discovermeals.models.Meal

interface ISaver {
    fun addFavMeal(meal: Meal)
    fun removeMealFromFavs(meal: Meal)
    fun isMealFav(meal: Meal) : Boolean
    fun getAllFavMealIds() : Set<String>
    fun addSelectedIngredient(ingredient: Ingredient)
    fun removeSelectedIngredient(ingredient: Ingredient)
    fun getAllSelectedIngredients() : Set<String>
}