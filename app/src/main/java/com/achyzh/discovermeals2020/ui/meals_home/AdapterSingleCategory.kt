package com.achyzh.discovermeals2020.ui.meals_home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.achyzh.discovermeals2020.databinding.IngredientItemBinding
import com.achyzh.discovermeals2020.interfaces.ItemSelectable
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.ui.tools.ImageViewHelper
import com.achyzh.discovermeals2020.ui.tools.ItemViewHelper

class AdapterSingleCategory(
    val itemSelectable: ItemSelectable<Ingredient>
) : RecyclerView.Adapter<AdapterSingleCategory.IngredientHolder>() {
    private var ingredients: MutableList<Ingredient> = mutableListOf()

    fun overrideData(ingredients: List<Ingredient>) {
        this.ingredients.clear()
        this.ingredients.addAll(ingredients)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val itemBinding = IngredientItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    inner class IngredientHolder(
        private val binding: IngredientItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private val ingredientNameTV: TextView
        private val ingredientIV: ImageView
        private var ingredient: Ingredient? = null

        init {
            binding.root.setOnClickListener(this)
            ingredientNameTV = binding.ingredientTextView
            ingredientIV = binding.ingredientImageView
        }

        fun bind(ingredient: Ingredient) {
            this.ingredient = ingredient
            ingredientNameTV.text = ingredient.name
            ImageViewHelper.bindIngredientWithImageView(
                ingredientIV,
                ingredient
            )
            val context = binding.root.context
            fill(context, ingredient)
        }

        private fun fill(context: Context, ingredient: Ingredient) {
            val isFilled = ingredient.isFill
            ItemViewHelper.setColorFilterToImageView(context, ingredientIV, isFilled)
            ItemViewHelper.setFillToNameTextView(context, ingredientNameTV, isFilled)
        }

        override fun onClick(v: View) {
            val isSelected = !(ingredient!!.isFill)
            ingredient!!.isFill = isSelected
            fill(v.context, ingredient!!)

            ingredient?.let {
                itemSelectable.onItemSelected(it)
            }
        }
    }
}