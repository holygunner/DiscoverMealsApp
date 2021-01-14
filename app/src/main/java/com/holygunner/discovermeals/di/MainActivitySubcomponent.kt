package com.holygunner.discovermeals.di

import com.holygunner.discovermeals.MainActivity
import com.holygunner.discovermeals.interfaces.ItemSelectable
import com.holygunner.discovermeals.models.Meal
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