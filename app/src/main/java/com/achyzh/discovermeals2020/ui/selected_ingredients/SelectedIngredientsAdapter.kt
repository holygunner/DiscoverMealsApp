package com.achyzh.discovermeals2020.ui.selected_ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.achyzh.discovermeals2020.databinding.IngredientItemV2Binding
import com.achyzh.discovermeals2020.interfaces.ItemSelectable
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.ui.tools.ImageViewHelper
import com.achyzh.discovermeals2020.ui.tools.ItemViewHelper

class SelectedIngredientsAdapter(
    val itemSelectable: ItemSelectable<Ingredient>,
    val itemsToDel: HashSet<Ingredient>) :

    RecyclerView.Adapter<SelectedIngredientsAdapter.IngredientHolder>() {

    private var ingredients = mutableListOf<Ingredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val binding = IngredientItemV2Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientHolder(binding)
    }

    fun refreshDataWithAnimation(updIngredients: List<Ingredient>) {
        if (this.ingredients.isEmpty()) {
            this.ingredients.addAll(updIngredients)
            notifyDataSetChanged()
        }   else {
            val iterator = this.ingredients.iterator()
            while (iterator.hasNext()) {
                val ingr = iterator.next()
                if (!updIngredients.contains(ingr)) {
                    val indx = this.ingredients.indexOf(ingr)
                    if (indx >= 0) {
                        iterator.remove()
                        notifyItemRemoved(indx)
                        notifyItemRangeChanged(indx, this.ingredients.size)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    inner class IngredientHolder(val binding: IngredientItemV2Binding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private val ingredientIV = binding.ingredientImageView
        private val ingredientTV = binding.ingredientTextView
        var ingredient : Ingredient? = null

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(ingredient: Ingredient) {
            this.ingredient = ingredient
            ImageViewHelper.bindIngredientWithImageView(
                ingredientIV, ingredient
            )
            ingredientTV.text = ingredient.name
            refreshColorFill()
        }

        override fun onClick(v: View) {
            val contains = itemsToDel.contains(ingredient)
            if (contains)
                itemsToDel.remove(ingredient)
            else
                ingredient?.let { itemsToDel.add(it) }

            itemSelectable.onItemSelected(ingredient!!)

            refreshColorFill()
        }

        private fun refreshColorFill() {
            val toDell = itemsToDel.contains(ingredient)
            val context = itemView.context
            ItemViewHelper.setColorFilterToImageView(context, ingredientIV, toDell)
            ItemViewHelper.setFillToNameTextView(context, ingredientTV, toDell)
        }
    }
}