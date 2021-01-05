package com.achyzh.discovermeals2020.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achyzh.discovermeals2020.ui.fav_meals.FavMealsViewModel
import com.achyzh.discovermeals2020.ui.meal_recipe.MealRecipeViewModel
import com.achyzh.discovermeals2020.ui.meals_home.MealsHomeViewModel
import com.achyzh.discovermeals2020.ui.meals_result.MealsResultViewModel
import com.achyzh.discovermeals2020.ui.search.SearchViewModel
import com.achyzh.discovermeals2020.ui.selected_ingredients.SelectedIngredientsViewModel
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