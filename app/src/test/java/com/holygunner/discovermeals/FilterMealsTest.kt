package com.holygunner.discovermeals

import com.holygunner.discovermeals.models.Ingredient
import com.holygunner.discovermeals.models.Meal
import com.holygunner.discovermeals.business_logic.MealsFilter
import com.holygunner.discovermeals.ui.meals_result.MealsResultViewModel
import org.junit.Test
import org.mockito.Mockito

class FilterMealsTest {

    @Test
    fun isFilterMealsCorrect() {
        val viewModel = Mockito.mock(MealsResultViewModel::class.java)
        val mealFilter = MealsFilter()

        val soda = Ingredient(name = "soda")
        val water = Ingredient(name = "water")
        val ingr1 = Ingredient(name = "ingr1")
        val ingr2 = Ingredient(name = "ingr2")

        val cake1 = Meal(id = 1, name = "cake")
        cake1.ingredientsList = mutableListOf(soda, ingr1)
        val cake2= Meal(id = 1, name = "cake")
        cake2.ingredientsList = mutableListOf(water, ingr2)

        val bread = Meal(id = 2, name = "bread")
        bread.ingredientsList = mutableListOf(soda, water)
        val pudding = Meal(id = 3, name = "pudding")

        val meals = mutableListOf(cake1, cake2, bread, pudding)

        val filteredMeals = mealFilter.filterMeals(meals)

        for (meal in filteredMeals) {
            println("${meal.name} ${meal.ingredientsList}")
        }
    }
}