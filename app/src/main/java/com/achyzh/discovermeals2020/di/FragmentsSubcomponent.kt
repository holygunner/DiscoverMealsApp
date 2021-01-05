package com.achyzh.discovermeals2020.di

import com.achyzh.discovermeals2020.ui.fav_meals.FavMealsFragment
import com.achyzh.discovermeals2020.ui.meal_recipe.MealRecipeFragment
import com.achyzh.discovermeals2020.ui.meals_home.MealsHomeFragment
import com.achyzh.discovermeals2020.ui.meals_result.MealsResultFragment
import com.achyzh.discovermeals2020.ui.search.SearchFragment
import com.achyzh.discovermeals2020.ui.selected_ingredients.SelectedIngredientsFragment
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