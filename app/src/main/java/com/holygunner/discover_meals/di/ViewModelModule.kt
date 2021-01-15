package com.holygunner.discover_meals.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.holygunner.discover_meals.ui.fav_meals.FavMealsViewModel
import com.holygunner.discover_meals.ui.meal_recipe.MealRecipeViewModel
import com.holygunner.discover_meals.ui.meals_home.MealsHomeViewModel
import com.holygunner.discover_meals.ui.meals_result.MealsResultViewModel
import com.holygunner.discover_meals.ui.search.SearchViewModel
import com.holygunner.discover_meals.ui.selected_ingredients.SelectedIngredientsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavMealsViewModel::class)
    abstract fun bindsFavMealsViewModel(viewModel: FavMealsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MealRecipeViewModel::class)
    abstract fun bindsMealRecipeViewModel(viewModel: MealRecipeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MealsHomeViewModel::class)
    abstract fun bindsMealsHomeViewModel(viewModel: MealsHomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MealsResultViewModel::class)
    abstract fun bindsMealsResultViewModel(viewModel: MealsResultViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindsSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectedIngredientsViewModel::class)
    abstract fun bindsSelectedIngredientsViewModel(viewModel: SelectedIngredientsViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}