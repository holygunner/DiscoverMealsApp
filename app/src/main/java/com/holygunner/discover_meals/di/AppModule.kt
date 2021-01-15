package com.holygunner.discover_meals.di

import android.content.Context
import com.holygunner.discover_meals.business_logic.IngredientManager
import com.holygunner.discover_meals.repository.*
import com.holygunner.discover_meals.repository.assets_res.AssetsAdapter
import com.holygunner.discover_meals.repository.network.BackendAPI
import com.holygunner.discover_meals.repository.network.BackendApiFactory
import com.holygunner.discover_meals.repository.network.BackendApiManager
import com.holygunner.discover_meals.tools.NetworkAccessibility
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDbWrapper(context: Context, realm: Realm): DbWrapper {
        Realm.init(context)
        return DbWrapper(context, realm)
    }

    @Singleton
    @Provides
    fun provideRepository(backendAPI: BackendAPI, saver: ISaver): RepositoryComponent {
        return RepositoryComponent(backendAPI, saver)
    }

    @Singleton
    @Provides
    fun provideIngredientManager(assetsAdapter: AssetsAdapter): IngredientManager {
        return IngredientManager(assetsAdapter)
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
    fun provideBackendApiManager(backendAPI: BackendAPI,
                                 networkAccessibility: NetworkAccessibility): BackendApiManager {
        return BackendApiManager(backendAPI, networkAccessibility)
    }

    @Singleton
    @Provides
    fun provideSaver(context: Context): ISaver {
        return SharedPrefSaver(context)
    }

    @Singleton
    @Provides
    fun provideRealm(context: Context): Realm {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm
            .setDefaultConfiguration(config)
        return Realm.getDefaultInstance()
    }

    @Singleton
    @Provides
    fun provideNetworkAccessibility(context: Context) : NetworkAccessibility {
        return NetworkAccessibility(context)
    }
}