package com.achyzh.discovermeals2020.di

import com.achyzh.discovermeals2020.MainActivity
import com.achyzh.discovermeals2020.interfaces.ItemSelectable
import com.achyzh.discovermeals2020.models.Meal
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