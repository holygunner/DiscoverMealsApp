package com.holygunner.discover_meals.business_logic

import com.holygunner.discover_meals.models.Ingredient

object IngredientUtil {

    fun fileNamesToIngredients(
        fileNames: Array<String>,
        category: String
    ): List<Ingredient> {
        val ingredients: MutableList<Ingredient> = mutableListOf()
        for (fileName in fileNames) {
            val name = fileName.replace(".png", "");
            val ingredient = Ingredient(name = name, category = category)
            ingredients.add(ingredient)
        }
        setTitleElemFirstIfExists(ingredients, category)
        return ingredients
    }

    private fun setTitleElemFirstIfExists(
        ingredients: MutableList<Ingredient>,
        category: String
    ) {
        var indx = 0
        for (i in ingredients.indices) {
            if (ingredients[i].name == category) {
                indx = i
                break
            }
        }
        if (indx != 0) {
            val titleIngredient = ingredients[indx]
            ingredients.removeAt(indx)
            ingredients.add(0, titleIngredient)
        }
    }
}