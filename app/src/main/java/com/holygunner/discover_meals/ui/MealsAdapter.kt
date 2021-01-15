package com.holygunner.discover_meals.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.holygunner.discover_meals.databinding.MealCardItemBinding
import com.holygunner.discover_meals.interfaces.ItemSelectable
import com.holygunner.discover_meals.models.Meal
import com.holygunner.discover_meals.business_logic.RecipeFactory
import com.holygunner.discover_meals.ui.tools.ImageViewHelper
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class MealsAdapter(
    val iSelectMeal: ItemSelectable<Meal>) :
    RecyclerView.Adapter<MealsAdapter.MealsHolder>() {
    val meals: MutableList<Meal> = mutableListOf()

    fun refreshData(meals: List<Meal>) {
        this.meals.clear()
        this.meals.addAll(meals)
        notifyDataSetChanged()
    }

    fun refreshFavs(favs: Set<Meal>) {
        for (meal in meals) {
            meal.isFav = favs.contains(meal)
            for (fav in favs) {
                meal.isFav = fav.id == meal.id
            }
        }
        notifyDataSetChanged()
    }

    fun positionToMeal(position: Int) : Meal {
        return meals[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsHolder {
        val itemBinding = MealCardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MealsHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MealsHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal, position)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    fun onItemRemoved(position: Int) {
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, meals.size)
    }

    inner class MealsHolder(binding: MealCardItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private val mealNameTV: TextView = binding.mealNameTextView
        private val ingredientsTV: TextView = binding.mealIngredientsTextView
        private val cardPageNumbTV: TextView = binding.mealPosition
        private val mealIV: ImageView = binding.mealImageView
        private val favView: View = binding.isMealLikedContainer
        private var meal: Meal? = null

        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap != null) {
                    val roundedBitmap = ImageViewHelper.createRoundedBitmap(bitmap)
                    mealIV.setImageBitmap(roundedBitmap)
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                e?.printStackTrace()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                if (placeHolderDrawable != null)
                    mealIV.setImageDrawable(placeHolderDrawable)
            }
        }

        init {
            val itemView = binding.root
            itemView.setOnClickListener(this)
        }

        fun bind(meal: Meal, position: Int) {
            cardPageNumbTV.text = (position + 1).toString()
            this.meal = meal
            setIsFav(meal)
//            ImageViewHelper.loadToImageView(mealIV, meal.urlImage)
            ImageViewHelper.loadToImageView(target, meal.urlImage)
            mealNameTV.text = meal.name
            initIngredientsTV(meal)
        }

        private fun setIsFav(meal: Meal, isFav: Boolean = meal.isFav) {
            favView.visibility = if (isFav) View.VISIBLE else View.GONE
        }

        private fun initIngredientsTV(meal: Meal) {
            val text = StringBuilder()
            val recipe = RecipeFactory.build(meal)
            if (recipe.isNotEmpty()) {
                for (pair in recipe) {
                    text.append("${pair.key} - ${pair.value}")
                        .append("\n")
                }
            }   else {
                for (ingr in meal.ingredientsList) {
                    if (ingr.measure != null)
                        text.append("${ingr.name} - ${ingr.measure}")
                            .append("\n")
                    else
                        text.append("${ingr.name}")
                            .append("\n")
                }
            }
            ingredientsTV.text = text
        }

        override fun onClick(v: View) {
            meal?.let { iSelectMeal.onItemSelected(it) }
        }
    }
}