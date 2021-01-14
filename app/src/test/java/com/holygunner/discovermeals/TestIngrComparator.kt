package com.holygunner.discovermeals

import com.holygunner.discovermeals.business_logic.IngredientsComparator
import com.holygunner.discovermeals.models.Ingredient
import org.junit.Test

class TestIngrComparator {

    @Test
    fun isSortCorrect() {
        val ingrList = mutableListOf<Ingredient>(Ingredient(name = "bb") , Ingredient(name = "aa"), Ingredient(name = "cc"))
        val sortedList = ingrList.sortedWith(IngredientsComparator())
        println(sortedList)
    }
}