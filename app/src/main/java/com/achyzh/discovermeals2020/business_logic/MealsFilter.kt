package com.achyzh.discovermeals2020.business_logic

import com.achyzh.discovermeals2020.models.Meal

class MealsFilter {

    fun filterMeals(meals: List<Meal>) : List<Meal> {
        val filteredMeals = meals.distinctBy { it.name }
        for (meal in meals) {
            for (filterMeal in filteredMeals) {
                if (filterMeal.name == meal.name) {
                    filterMeal.ingredientsList.addAll(meal.ingredientsList)
                    val list = filterMeal.ingredientsList.distinctBy { it.name }
                    filterMeal.ingredientsList.clear()
                    filterMeal.ingredientsList.addAll(list)
                }
            }
        }
        return filteredMeals
            .sortedBy { it.name }
            .sortedByDescending { it.ingredientsList.size }
    }
}