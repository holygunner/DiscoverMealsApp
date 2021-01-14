package com.holygunner.discover_meals.di

import android.content.Context
import com.holygunner.discover_meals.business_logic.IngredientManager
import com.holygunner.discover_meals.repository.DbWrapper
import com.holygunner.discover_meals.repository.ISaver
import com.holygunner.discover_meals.repository.RepositoryComponent
import com.holygunner.discover_meals.repository.network.BackendAPI
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AppSubcomponents::class,
    ViewModelModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun provideContext(): Context
    fun provideDbWrapper(): DbWrapper
    fun provideRepository(): RepositoryComponent
    fun provideIngredientManager(): IngredientManager
    fun provideBackendAPI(): BackendAPI
    fun provideSaver(): ISaver
    fun mainActivitySubcomponent() : MainActivitySubcomponent.Factory
    fun fragmentSubcomponent() : FragmentsSubcomponent.Factory
}