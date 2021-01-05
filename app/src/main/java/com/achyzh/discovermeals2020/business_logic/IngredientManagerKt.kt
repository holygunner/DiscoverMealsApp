package com.achyzh.discovermeals2020.business_logic

import android.graphics.drawable.Drawable
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.models.IngredientsCategory
import com.achyzh.discovermeals2020.repository.io.AssetsAdapter
import com.achyzh.discovermeals2020.values.IngredientsCategories.CATEGORIES_NAMES
import java.util.*
import javax.inject.Inject

class IngredientManagerKt
@Inject constructor(
    private val assetsAdapter: AssetsAdapter
) {

    companion object {
        fun verifyIngredientMeasure(measure: String): Boolean {
            return (measure != "\n"
                    && measure != " " && measure != "")
        }
    }

    fun buildIngredientCategories(): List<IngredientsCategory>? {
        val allIngredients: MutableList<IngredientsCategory> = ArrayList()
        for (category in CATEGORIES_NAMES) {
            val ingredients: List<Ingredient>? = getIngredientsOfCategory(category)
            if (ingredients != null) {
                allIngredients.add(IngredientsCategory(category, ingredients))
            }
        }
        return allIngredients
    }

    fun getAllIngredientCategories(): List<IngredientsCategory>? {
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

    fun findIngredientCategory(fileName: String?): String? {
        return assetsAdapter.findIngredientCategory(fileName!!)
    }

    private fun getIngredientsOfCategory(category: String): List<Ingredient>? {
        return assetsAdapter.getIngredientsOfCategory(category)
    }
}