package com.achyzh.discovermeals2020.di

import android.content.Context
import com.achyzh.discovermeals2020.business_logic.IngredientManagerKt
import com.achyzh.discovermeals2020.repository.*
import com.achyzh.discovermeals2020.repository.io.AssetsAdapter
import com.achyzh.discovermeals2020.repository.network.BackendAPI
import com.achyzh.discovermeals2020.repository.network.BackendApiFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDbWrapper(context: Context): DbWrapper {
        return DbWrapper(context)
    }

    @Singleton
    @Provides
    fun provideRepository(backendAPI: BackendAPI, saver: ISaver): RepositoryComponent {
        return RepositoryComponent(backendAPI, saver)
    }

    @Singleton
    @Provides
    fun provideIngredientManager(assetsAdapter: AssetsAdapter): IngredientManagerKt {
        return IngredientManagerKt(assetsAdapter)
    }

    @Singleton
    @Provides
    fun provideAssetsAdapter(context: Context) : AssetsAdapter {
        return AssetsAdapter(context)
    }

    @Singleton
    @Provides
    fun provideBackendAPI(): BackendAPI {
        return BackendApiFactory.create()
    }

    @Singleton
    @Provides
    fun provideSaver(context: Context): ISaver {
        return SharedPrefSaver(context)
    }


}