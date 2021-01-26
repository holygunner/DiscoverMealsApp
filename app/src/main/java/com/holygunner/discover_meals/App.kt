package com.holygunner.discover_meals

import android.app.Application
import androidx.work.Configuration
import com.holygunner.discover_meals.di.*
import timber.log.Timber

class App : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

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

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}