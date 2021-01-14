package com.holygunner.discover_meals.repository

import com.holygunner.discover_meals.models.Ingredient
import com.holygunner.discover_meals.models.Meal

interface ISaver {
    fun addFavMeal(meal: Meal)
    fun removeMealFromFavs(meal: Meal)
    fun isMealFav(meal: Meal) : Boolean
    fun getAllFavMealIds() : Set<String>
    fun addSelectedIngredient(ingredient: Ingredient)
    fun removeSelectedIngredient(ingredient: Ingredient)
    fun getAllSelectedIngredients() : Set<String>
}