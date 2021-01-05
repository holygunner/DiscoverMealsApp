package com.achyzh.discovermeals2020.repository

import android.content.Context
import android.widget.Toast
import com.achyzh.discovermeals2020.models.Meal
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmQuery
import javax.inject.Inject

class DbWrapper @Inject constructor(
    val context: Context
    ) {

    init {
        initRealm()
    }

    fun initRealm() {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .build()
        Realm.setDefaultConfiguration(config)
    }

    fun storeMeal(meal: Meal) {
        getRealmInstance().executeTransaction {
            val mealToStore = it.copyToRealm(meal)
        }
    }

    fun getStoredFavMeals(): RealmQuery<Meal> {
        val favMeals = getRealmInstance().where(Meal::class.java)
        return favMeals
    }

    private fun getRealmInstance(): Realm {
        return Realm.getDefaultInstance()
    }

    fun showToast() {
        Toast.makeText(
                context,
                "DbWrapper is initialized",
                Toast.LENGTH_LONG)
            .show()
    }
}