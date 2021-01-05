package com.achyzh.discovermeals2020.di

import android.content.Context
import com.achyzh.discovermeals2020.App
import com.achyzh.discovermeals2020.business_logic.IngredientManagerKt
import com.achyzh.discovermeals2020.repository.DbWrapper
import com.achyzh.discovermeals2020.repository.ISaver
import com.achyzh.discovermeals2020.repository.RepositoryComponent
import com.achyzh.discovermeals2020.repository.network.BackendAPI
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
    fun provideIngredientManager(): IngredientManagerKt
    fun provideBackendAPI(): BackendAPI
    fun provideSaver(): ISaver
    fun mainActivitySubcomponent() : MainActivitySubcomponent.Factory
    fun fragmentSubcomponent() : FragmentsSubcomponent.Factory
}