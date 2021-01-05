package com.achyzh.discovermeals2020.ui.meals_home

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.achyzh.discovermeals2020.R
import com.achyzh.discovermeals2020.databinding.IngredientsSectionCardItemBinding
import com.achyzh.discovermeals2020.interfaces.ItemSelectable
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.values.IngredientsCategories
import com.achyzh.discovermeals2020.models.IngredientsCategory
import com.achyzh.discovermeals2020.ui.tools.ItemViewHelper

class AdapterCategories (
    private val itemSelectable: ItemSelectable<Ingredient>
) :
    RecyclerView.Adapter<AdapterCategories.CategoryHolder>() {

    private val categories : MutableList<IngredientsCategory> = mutableListOf()
    private val recycledViewPool : RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    init {
        recycledViewPool.setMaxRecycledViews(
            R.id.ingredients_recyclerView,
            IngredientsCategories.CATEGORIES_NAMES.size
        )
    }

    fun overrideData(categories: List<IngredientsCategory>) {
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    fun getBackCategories() : MutableList<IngredientsCategory> {
        return categories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemBinding = IngredientsSectionCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = CategoryHolder(itemBinding)
        itemBinding.ingredientsRecyclerView.setRecycledViewPool(recycledViewPool)
        return holder
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val context = holder.itemBinding.root.context
        val categoryName = categories[position].categoryName
        val ingredients = categories[position].ingredients
        holder.itemBinding.categoryTextView.text = categoryName
        val adapter = AdapterSingleCategory(itemSelectable)
        adapter.overrideData(ingredients.toMutableList())
        val recyclerView = holder.itemBinding.ingredientsRecyclerView
        recyclerView.setHasFixedSize(true)
        val orientation = context.resources.configuration.orientation

        val manager: RecyclerView.LayoutManager

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            manager = GridLayoutManager(
                context, ItemViewHelper.calculateNumbOfColumns(context),
                GridLayoutManager.HORIZONTAL,
                false)
            if (recyclerView.onFlingListener == null) {
                val linearSnapHelper = LinearSnapHelper()
                linearSnapHelper.attachToRecyclerView(recyclerView)
            }
        } else {
            manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoryHolder(
        val itemBinding: IngredientsSectionCardItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}