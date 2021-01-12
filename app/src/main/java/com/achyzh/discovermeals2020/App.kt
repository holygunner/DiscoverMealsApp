package com.achyzh.discovermeals2020

import android.app.Application
import com.achyzh.discovermeals2020.di.*

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