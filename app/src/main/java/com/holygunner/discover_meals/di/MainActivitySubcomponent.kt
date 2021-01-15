package com.holygunner.discover_meals.di

import com.holygunner.discover_meals.MainActivity
import com.holygunner.discover_meals.interfaces.ItemSelectable
import com.holygunner.discover_meals.models.Meal
import dagger.Subcomponent

@RuntimeScope
@Subcomponent
interface MainActivitySubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivitySubcomponent
    }

    fun inject(mainActivity: MainActivity)
}