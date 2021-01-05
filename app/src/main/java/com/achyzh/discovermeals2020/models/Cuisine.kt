package com.achyzh.discovermeals2020.models

import io.realm.RealmObject

data class Cuisine(var meals: Array<Meal>)
//    : RealmObject()
{
    constructor() : this(arrayOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cuisine

        if (!meals.contentEquals(other.meals)) return false

        return true
    }

    override fun hashCode(): Int {
        return meals.contentHashCode()
    }
}
