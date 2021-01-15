package com.holygunner.discover_meals

import com.holygunner.discover_meals.business_logic.IngredientsComparator
import com.holygunner.discover_meals.models.Ingredient
import org.junit.Test

class TestIngrComparator {

    @Test
    fun isSortCorrect() {
        val ingrList = mutableListOf<Ingredient>(Ingredient(name = "bb") , Ingredient(name = "aa"), Ingredient(name = "cc"))
        val sortedList = ingrList.sortedWith(IngredientsComparator())
        println(sortedList)
    }
}