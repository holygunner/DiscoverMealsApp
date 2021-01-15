package com.holygunner.discover_meals.repository

import android.R.id
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.holygunner.discover_meals.models.Ingredient
import com.holygunner.discover_meals.models.Meal
import com.holygunner.discover_meals.models.UserSelection
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.executeTransactionAwait
import io.realm.kotlin.toFlow
import io.realm.kotlin.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class DbWrapper @Inject constructor(
    val context: Context,
    val realm: Realm
) {

    suspend fun storeUserSelection(userSelection: UserSelection) {
        realm.executeTransactionAwait {
            if (userSelection.isValid) {
                it.insertOrUpdate(userSelection)
            }
        }
    }

    suspend fun getUserSelection(): UserSelection {
        return realm.where(UserSelection::class.java)
            .findFirstAsync()
    }

    suspend fun getFilledIngredients(): List<Ingredient> {
        return realm
            .where<Ingredient>()
            .equalTo("isFill", true)
            .findAllAsync()
    }

    suspend fun copyOrUpdateMeal2(meal: Meal, isFav: Boolean = false) {
        realm.executeTransactionAwait {
            it.insertOrUpdate(meal)
        }
    }

    suspend fun copyOrUpdateMeal(mealId: Int, isFav: Boolean = false) {
        val storedMeal: Meal? = realm.where(Meal::class.java)
            .equalTo("id", mealId)
            .findFirst()
        storedMeal?.load()

        if (storedMeal != null) {
            val copy = realm.copyFromRealm(storedMeal)
            copy.isFav = isFav
            realm.executeTransactionAwait {
                it.insertOrUpdate(copy)
            }
        }
    }

    suspend fun copyOrUpdateMeal(meal: Meal, isFav: Boolean = false) {
        val storedMeal: Meal? = realm.where(Meal::class.java)
            .equalTo("id", meal.id)
            .findFirst()
        storedMeal?.load()

        if (storedMeal != null && meal.isValid) {
            val copy = realm.copyFromRealm(storedMeal)
            copy.isFav = isFav
            realm.executeTransactionAwait {
                it.insertOrUpdate(copy)
            }
        } else {
            realm.executeTransactionAwait {
                meal.isFav = isFav
                it.insertOrUpdate(meal)
            }
        }
    }

    suspend fun storeIngrsWithAssociatedMeals(ingredient: Ingredient) {
        realm.executeTransactionAwait {
            it.insertOrUpdate(ingredient)
        }
    }

    fun getAllIngredients(): LiveData<List<Ingredient>> {
        return liveData {
            realm.where(Ingredient::class.java)
                .findAllAsync()
                .toFlow()
                .collect { emit(it) }
        }
    }

    fun getMealLD(id: Int): LiveData<List<Meal>> {
        return liveData {
            realm.where<Meal>()
                .equalTo("id", id)
                .findAllAsync()
                .toFlow()
                .collect { emit(it) }
        }
    }

    fun getFavMealsLD(): LiveData<List<Meal>> {
        return liveData {
            realm.where<Meal>()
                .equalTo("isFav", true)
                .findAllAsync()
                .toFlow()
                .collect { emit(it) }
        }
    }



    fun getAllIngredientsAsync(): RealmResults<Ingredient> {
        return realm.where(Ingredient::class.java)
            .findAllAsync()
    }

    suspend fun getAllFetchedIngredientsAsync(): RealmResults<Ingredient> {
        return realm.where<Ingredient>()
            .equalTo("isFetched", true)
            .findAllAsync()
    }

    suspend fun getMealAsync(id: Int): Meal {
        return realm.where<Meal>()
            .equalTo("id", id)
            .findFirstAsync()
    }

    suspend fun storeMeals(meals: MutableList<Meal>) {
        realm.executeTransactionAwait {
            it.insertOrUpdate(meals)
        }
    }

    suspend fun copyStoredMeal(meal: Meal) : Meal {
        return realm.copyFromRealm(meal)
    }

    suspend fun updStoredMeal(meal: Meal) {
        val stored = realm.copyFromRealm(meal)
        realm.executeTransactionAwait {
            it.insertOrUpdate(stored)
        }
    }

    suspend fun getAllMealsAsync(): List<Meal> {
        return realm.where(Meal::class.java)
            .findAllAsync()
    }

    fun getAllMeals(): LiveData<List<Meal>> {
        return liveData {
            realm.where(Meal::class.java)
                .findAllAsync()
                .toFlow()
                .collect { emit(it) }
        }
    }

    fun getFavMeals(): LiveData<List<Meal>> {
        return liveData {
            realm.where<Meal>().equalTo("isFav", true)
                .findAllAsync()
                .toFlow()
                .collect { emit(it) }
        }
    }

    fun getFavMealsAsync(): List<Meal> {
        return realm.where<Meal>()
            .equalTo("isFav", true)
            .findAllAsync()
    }

    fun getStoredFavMeals(): RealmQuery<Meal> {
        val favMeals = realm.where(Meal::class.java)
        return favMeals
    }

    fun getRealmInstance(): Realm {
        return Realm.getDefaultInstance()
    }
}