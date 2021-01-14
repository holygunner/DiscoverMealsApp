package com.holygunner.discovermeals.di

import android.content.Context
import com.holygunner.discovermeals.business_logic.IngredientManager
import com.holygunner.discovermeals.repository.DbWrapper
import com.holygunner.discovermeals.repository.ISaver
import com.holygunner.discovermeals.repository.RepositoryComponent
import com.holygunner.discovermeals.repository.network.BackendAPI
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