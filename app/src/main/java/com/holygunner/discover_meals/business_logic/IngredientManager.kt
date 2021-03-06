package com.holygunner.discover_meals.business_logic

import android.graphics.drawable.Drawable
import com.holygunner.discover_meals.models.Ingredient
import com.holygunner.discover_meals.models.IngredientsCategory
import com.holygunner.discover_meals.repository.assets_res.AssetsAdapter
import com.holygunner.discover_meals.values.IngredientsCategories.CATEGORIES_NAMES
import java.util.*
import javax.inject.Inject

class IngredientManager
@Inject constructor(
    private val assetsAdapter: AssetsAdapter
) {

    companion object {
        fun verifyIngredientMeasure(measure: String): Boolean {
            return measure.isNotBlank()
        }
    }

    fun buildIngredientCategories(): List<IngredientsCategory> {
        val allIngredients: MutableList<IngredientsCategory> = ArrayList()
        for (category in CATEGORIES_NAMES) {
            val ingredients: List<Ingredient>? = getIngredientsOfCategory(category)
            if (ingredients != null) {
                allIngredients.add(IngredientsCategory(category, ingredients))
            }
        }
        return allIngredients
    }

    fun getAllIngredientCategories(): List<IngredientsCategory> {
        val allIngredients: MutableList<IngredientsCategory> = ArrayList()
        for (category in CATEGORIES_NAMES) {
            val ingredients = getIngredientsOfCategory(category)
            if (ingredients != null) {
                allIngredients.add(IngredientsCategory(category, ingredients))
            }
        }
        return allIngredients
    }

    fun getIngredientDrawable(folderName: String?, fileName: String?): Drawable? {
        return assetsAdapter.getIngredientDrawable(folderName, fileName)
    }

    fun findIngredientCategory(fileName: String?): String {
        return assetsAdapter.findIngredientCategory(fileName!!) ?: ""
    }

    private fun getIngredientsOfCategory(category: String): List<Ingredient>? {
        return assetsAdapter.getIngredientsOfCategory(category)
    }
}