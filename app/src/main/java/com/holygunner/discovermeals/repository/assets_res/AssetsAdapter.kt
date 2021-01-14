package com.holygunner.discovermeals.repository.assets_res

import android.content.Context
import android.graphics.drawable.Drawable
import com.holygunner.discovermeals.business_logic.IngredientUtil.fileNamesToIngredients
import com.holygunner.discovermeals.models.Ingredient
import com.holygunner.discovermeals.values.IngredientsCategories
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class AssetsAdapter @Inject constructor(appContext: Context) {
    private val assetManager = appContext.assets

    fun findIngredientCategory(fileName: String): String? {
        try {
            for (category in IngredientsCategories.CATEGORIES_NAMES) {
                val array = assetManager.list(category)
                val set = array!!.toSortedSet()
                if (set.contains("$fileName.png"))
                    return category
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getIngredientsOfCategory(category: String): List<Ingredient>? {
        return try {
            val names = assetManager.list(category)
            fileNamesToIngredients(names!!, category)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun getIngredientDrawable(folderName: String?, fileName: String?): Drawable? {
        return try {
            val inputStream: InputStream =
                assetManager.open(getRightFileName(folderName!!, fileName!!))
            Drawable.createFromStream(inputStream, null)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getRightFileName(folderName: String, fileName: String): String {
        return "$folderName/$fileName.png"
    }
}