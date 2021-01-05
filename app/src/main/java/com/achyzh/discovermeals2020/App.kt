package com.achyzh.discovermeals2020

import android.app.Application
import android.content.Context
import com.achyzh.discovermeals2020.business_logic.IngredientManagerKt
import com.achyzh.discovermeals2020.di.*
import com.achyzh.discovermeals2020.repository.DbWrapper
import com.achyzh.discovermeals2020.repository.io.AssetsAdapter
import javax.inject.Inject

class App : Application() {

    /**
     * Instance of the AppComponent that will be used by all the Activities in the project
     */
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    /**
     * Creates an instance of AppComponent using its Factory constructor
     * We pass the applicationContext that will be used as Context in the graph
     */
    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent
            .factory()
            .create(applicationContext)
    }
}