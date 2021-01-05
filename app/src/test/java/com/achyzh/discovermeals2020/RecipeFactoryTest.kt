package com.achyzh.discovermeals2020

import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.business_logic.RecipeFactory
import junit.framework.Assert.assertTrue
import org.junit.Test

class RecipeFactoryTest {

    @Test
    fun isRecipeMapNotEmptyCase1() {
        val mealKt = Meal(1, "Cake")
        mealKt.strIngredient1 = "strIngredient1"
        mealKt.strIngredient2 = "strIngredient1"
        mealKt.strIngredient3 = "strIngredient1"
        mealKt.strIngredient4 = "strIngredient1"
        mealKt.strIngredient5 = "strIngredient1"

        mealKt.strMeasure1 = "strMeasure1"
        mealKt.strMeasure2 = "strMeasure2"
        mealKt.strMeasure3 = "strMeasure3"
        mealKt.strMeasure4 = "strMeasure4"
        mealKt.strMeasure5 = "strMeasure5"

        val factoryToTest = RecipeFactory
        val recipeMap = factoryToTest.build(mealKt)
        assertTrue(recipeMap.isNotEmpty())
    }

    @Test
    fun isRecipeMapNotEmptyCase2() {
        val mealKt = Meal(1, "Cake")
        mealKt.strIngredient1 = "strIngredient1"
        mealKt.strIngredient2 = null

        mealKt.strMeasure1 = "strMeasure1"
        mealKt.strMeasure2 = "strMeasure2"
        mealKt.strMeasure3 = "strMeasure3"
        mealKt.strMeasure4 = "strMeasure4"
        mealKt.strMeasure5 = "strMeasure5"

        val factoryToTest = RecipeFactory
        val recipeMap = factoryToTest.build(mealKt)
        assertTrue(recipeMap.isNotEmpty())
    }

    @Test
    fun isRecipeListNotEmptyCase2() {
        val mealKt = Meal(1, "Cake")
        mealKt.strIngredient1 = "strIngredient1"
        mealKt.strIngredient2 = null

        mealKt.strMeasure1 = "strMeasure1"
        mealKt.strMeasure2 = "strMeasure2"
        mealKt.strMeasure3 = "strMeasure3"
        mealKt.strMeasure4 = "strMeasure4"
        mealKt.strMeasure5 = "strMeasure5"

        val factoryToTest = RecipeFactory
        val list = factoryToTest.buildIngredientList(mealKt)
        assertTrue(list.isNotEmpty())
    }
}