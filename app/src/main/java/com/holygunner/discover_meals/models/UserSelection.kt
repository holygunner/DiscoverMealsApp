package com.holygunner.discover_meals.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserSelection(
    @PrimaryKey
    var id: String = "",
    var selectedIngredients : RealmList<String> = RealmList()
) : RealmObject() {

    constructor(builder: Builder) : this(builder.id, builder.selectedIngredients)

    fun refreshContent(selectedIngredients: List<String>) {
        this.selectedIngredients.clear()
        this.selectedIngredients.addAll(selectedIngredients)
    }

    class Builder {
        var id: String = UserSelection::class.java.simpleName
        var selectedIngredients : RealmList<String> =  RealmList()

        fun selectedIngredients(selectedIngredientNames : RealmList<String>) =
            apply { this.selectedIngredients = selectedIngredientNames }

        fun build() = UserSelection(id, selectedIngredients)
    }
}