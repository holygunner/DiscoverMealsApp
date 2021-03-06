package com.holygunner.discover_meals.di

import com.holygunner.discover_meals.ui.fav_meals.FavMealsFragment
import com.holygunner.discover_meals.ui.meal_recipe.MealRecipeFragment
import com.holygunner.discover_meals.ui.meals_home.MealsHomeFragment
import com.holygunner.discover_meals.ui.meals_result.MealsResultFragment
import com.holygunner.discover_meals.ui.search.SearchFragment
import com.holygunner.discover_meals.ui.selected_ingredients.SelectedIngredientsFragment
import dagger.Subcomponent

@RuntimeScope
@Subcomponent
interface FragmentsSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FragmentsSubcomponent
    }

    fun inject(fragment: FavMealsFragment)
    fun inject(fragment: MealRecipeFragment)
    fun inject(fragment: MealsHomeFragment)
    fun inject(fragment: SelectedIngredientsFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: MealsResultFragment)
}