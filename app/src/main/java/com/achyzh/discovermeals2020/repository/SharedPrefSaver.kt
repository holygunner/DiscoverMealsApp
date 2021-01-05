package com.achyzh.discovermeals2020.repository

import android.content.Context
import android.content.SharedPreferences
import com.achyzh.discovermeals2020.App
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.models.Meal
import javax.inject.Inject

class SharedPrefSaver
@Inject constructor(context: Context) : ISaver {
    private val sharedPreferences: SharedPreferences = context
        .getSharedPreferences(APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        const val FAV_MEALS_KEY = "FAV_MEALS_KEY"
        const val SELECTED_INGR_KEY = "SELECTED_INGR_KEY"
        const val APP_SHARED_PREF_NAME = "AppSharedPrefName"
    }

    override fun addFavMeal(meal: Meal) {
        val favMealsSet = sharedPreferences.getStringSet(FAV_MEALS_KEY, hashSetOf())
        favMealsSet?.add(meal.id.toString())
        sharedPreferences
            .edit()
            .putStringSet(FAV_MEALS_KEY, favMealsSet)
            .apply()
    }

    override fun removeMealFromFavs(meal: Meal) {
        val favMealsSet = sharedPreferences.getStringSet(FAV_MEALS_KEY, hashSetOf())
        favMealsSet?.remove(meal.id.toString())
        sharedPreferences
            .edit()
            .putStringSet(FAV_MEALS_KEY, favMealsSet)
            .apply()
    }

    override fun isMealFav(meal: Meal): Boolean {
        val favMealsSet = getAllFavMealIds()
        return favMealsSet!!.contains(meal.id.toString())
    }

    override fun getAllFavMealIds(): Set<String> {
        return sharedPreferences.getStringSet(FAV_MEALS_KEY, hashSetOf()) ?: hashSetOf()
    }

    override fun addSelectedIngredient(ingredient: Ingredient) {
        val favMealsSet = sharedPreferences.getStringSet(SELECTED_INGR_KEY, hashSetOf())
        favMealsSet?.add(ingredient.name)
        sharedPreferences
            .edit()
            .putStringSet(SELECTED_INGR_KEY, favMealsSet)
            .apply()
    }

    override fun removeSelectedIngredient(ingredient: Ingredient) {
        val favMealsSet = sharedPreferences.getStringSet(SELECTED_INGR_KEY, hashSetOf())
        favMealsSet?.remove(ingredient.name.toString())
        sharedPreferences
            .edit()
            .putStringSet(SELECTED_INGR_KEY, favMealsSet)
            .apply()
    }

    override fun getAllSelectedIngredients(): Set<String> {
        return sharedPreferences.getStringSet(SELECTED_INGR_KEY, hashSetOf()) ?: hashSetOf()
    }
}