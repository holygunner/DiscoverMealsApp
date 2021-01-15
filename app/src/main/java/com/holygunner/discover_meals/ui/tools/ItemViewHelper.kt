package com.holygunner.discover_meals.ui.tools

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.holygunner.discover_meals.R

object ItemViewHelper {
    private const val MATERIAL_COEFFICIENT = 128

    fun setFillToNameTextView(
        context: Context,
        ingredientNameTextView: TextView,
        isFilled: Boolean
    ) {
        if (isFilled) {
            ingredientNameTextView.setTextColor(
                ContextCompat.getColor(context, R.color.material_grey50))
            ingredientNameTextView.background = ContextCompat
                .getDrawable(context, R.drawable.ingredient_name_fill)
        } else {
            ingredientNameTextView.setTextColor(ContextCompat.getColor(context,
                R.color.text_color))
            ingredientNameTextView.setBackgroundResource(0)
        }
    }

    fun setColorFilterToImageView(
        context: Context,
        ingredientImageView: ImageView,
        isFilled: Boolean
    ) {
        if (isFilled) {
            ingredientImageView.setColorFilter(
                ContextCompat.getColor(context, R.color.ingredient_color_fill)
            )
        } else {
            ingredientImageView.colorFilter = null
        }
    }

    fun calculateNumbOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / MATERIAL_COEFFICIENT).toInt()
    }
}