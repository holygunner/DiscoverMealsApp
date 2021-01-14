package com.holygunner.discovermeals.models

data class Cuisine(var meals: Array<Meal>) {
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
