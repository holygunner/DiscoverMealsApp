package com.achyzh.discovermeals2020.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.achyzh.discovermeals2020.databinding.MealCardItemBinding
import com.achyzh.discovermeals2020.interfaces.ItemSelectable
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.business_logic.RecipeFactory
import com.achyzh.discovermeals2020.ui.tools.ImageViewHelper

class MealsAdapterKt (
    val iSelectMeal: ItemSelectable<Meal>,
    val favMealIds : Set<String>) :
    RecyclerView.Adapter<MealsAdapterKt.MealsHolder>() {
    val meals: MutableList<Meal> = mutableListOf()
//    val favMealIds : Set<String> = Repository.saver.getAllFavMealIds()

    fun refreshData(meals: List<Meal>) {
        this.meals.clear()
        this.meals.addAll(meals)
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

        init {
            val itemView = binding.root
            itemView.setOnClickListener(this)
        }

        fun bind(meal: Meal, position: Int) {
            cardPageNumbTV.text = (position + 1).toString()
            this.meal = meal
            setIsFav(meal, favMealIds.contains(meal.id.toString()))
            initIngredientsTV(meal)
        }

        private fun setIsFav(meal: Meal, isFav: Boolean) {
            favView.visibility = if (isFav) View.VISIBLE else View.GONE
            mealIV.tag = ImageViewHelper.loadToImageView(mealIV, meal.urlImage)
            mealNameTV.text = meal.name
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