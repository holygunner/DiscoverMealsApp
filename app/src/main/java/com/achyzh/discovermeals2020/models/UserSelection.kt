package com.achyzh.discovermeals2020.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserSelection(
    @PrimaryKey
    var id: String = "",
    var selectedIngredients : RealmList<Ingredient> = RealmList()
) : RealmObject() {

    constructor(builder: Builder) : this(builder.id, builder.selectedIngredients)

    fun refreshContent(selectedIngredients: List<Ingredient>) {
        this.selectedIngredients.clear()
        this.selectedIngredients.addAll(selectedIngredients)
    }

    class Builder {
        var id: String = UserSelection::class.java.simpleName
        var selectedIngredients : RealmList<Ingredient> =  RealmList()

        fun selectedIngredients(selectedIngredientNames : RealmList<Ingredient>) =
            apply { this.selectedIngredients = selectedIngredientNames }

        fun build() = UserSelection(id, selectedIngredients)
    }
}