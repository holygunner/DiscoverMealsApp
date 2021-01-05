package com.achyzh.discovermeals2020

import com.achyzh.discovermeals2020.business_logic.IngredientsComparator
import com.achyzh.discovermeals2020.models.Ingredient
import org.junit.Test

class TestIngrComparator {

    @Test
    fun isSortCorrect() {
        val ingrList = mutableListOf<Ingredient>(Ingredient(name = "bb") , Ingredient(name = "aa"), Ingredient(name = "cc"))
        val sortedList = ingrList.sortedWith(IngredientsComparator())
        println(sortedList)
    }
}