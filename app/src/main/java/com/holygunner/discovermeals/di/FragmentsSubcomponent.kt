package com.holygunner.discovermeals.di

import com.holygunner.discovermeals.ui.fav_meals.FavMealsFragment
import com.holygunner.discovermeals.ui.meal_recipe.MealRecipeFragment
import com.holygunner.discovermeals.ui.meals_home.MealsHomeFragment
import com.holygunner.discovermeals.ui.meals_result.MealsResultFragment
import com.holygunner.discovermeals.ui.search.SearchFragment
import com.holygunner.discovermeals.ui.selected_ingredients.SelectedIngredientsFragment
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