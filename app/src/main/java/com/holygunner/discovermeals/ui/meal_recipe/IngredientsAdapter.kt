package com.holygunner.discovermeals.ui.meal_recipe

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.holygunner.discovermeals.business_logic.IngredientManager
import com.holygunner.discovermeals.databinding.IngredientItemV2Binding
import com.holygunner.discovermeals.models.Ingredient
import com.holygunner.discovermeals.ui.tools.ImageViewHelper
import com.squareup.picasso.Picasso

class IngredientsAdapter (
    val context: Context,
    val ingredientManager: IngredientManager
    ) : RecyclerView.Adapter<IngredientsAdapter.IngredientHolder>() {

    val ingredients = mutableListOf<Ingredient>()


    fun refreshData(ingredients: List<Ingredient>) {
        this.ingredients.clear()
        this.ingredients.addAll(ingredients)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val itemBinding = IngredientItemV2Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    inner class IngredientHolder(
        private val binding: IngredientItemV2Binding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            val category: String = ingredientManager.findIngredientCategory(ingredient.name)
            val drawable: Drawable? = ingredientManager
                .getIngredientDrawable(
                    category,
                    ingredient.name
                )
            val imageView = binding.ingredientImageView
            if (drawable != null) {
                imageView.setImageDrawable(drawable)
            } else {
                Picasso.get()
                    .load(ingredient.name?.let { ImageViewHelper.buildMissedIngrImageUrl(it) })
                    .into(imageView)
            }
            val text: String? =
                if (IngredientManager.verifyIngredientMeasure(ingredient.measure!!)) {
                    ingredient.name + ": " + ingredient.measure
                } else {
                    ingredient.name
                }
            val nameTV = binding.ingredientTextView
            nameTV.text = text
        }
    }
}