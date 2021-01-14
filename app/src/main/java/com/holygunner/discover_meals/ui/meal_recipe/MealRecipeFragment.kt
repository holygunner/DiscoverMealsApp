package com.holygunner.discover_meals.ui.meal_recipe

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.holygunner.discover_meals.R
import com.holygunner.discover_meals.business_logic.IngredientManager
import com.holygunner.discover_meals.business_logic.RecipeFactory
import com.holygunner.discover_meals.databinding.FragmentMealRecipeBinding
import com.holygunner.discover_meals.di.FragmentsSubcomponent
import com.holygunner.discover_meals.models.Meal
import com.holygunner.discover_meals.ui.BaseFragment
import com.holygunner.discover_meals.ui.tools.ImageViewHelper
import com.holygunner.discover_meals.ui.tools.ItemViewHelper
import com.holygunner.discover_meals.values.Keys.Companion.MEAL_PARCEL_KEY
import javax.inject.Inject


class MealRecipeFragment : BaseFragment() {
    private var _binding : FragmentMealRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mealViewModel: MealRecipeViewModel

    lateinit var ingredientsAdapter: IngredientsAdapter

    @Inject
    lateinit var ingredientManager: IngredientManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mealViewModel = ViewModelProvider(this, viewModelFactory).get(MealRecipeViewModel::class.java)
        _binding = FragmentMealRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        val meal : Meal? = arguments?.getParcelable(MEAL_PARCEL_KEY)
        mealViewModel.setMealId(meal!!.id)
        mealViewModel.loadData(meal)
        setRecyclerView()
        observeData()
        setFavView()
        return view
    }

    private fun setFavView() {
        binding.favImageButton.setOnClickListener {
            mealViewModel.invertMealFav()
        }
    }

    private fun setLikeImageButton(isFav: Boolean) {
        val favImageButton = binding.favImageButton
        if (isFav) {
            favImageButton.setImageResource(R.drawable.like_button_pressed)
        } else {
            favImageButton.setImageResource(R.drawable.like_button)
        }
    }

    private var expandable = false
    private var expand = false
    private val recipeMaxLines = 16
    private val maxLinesPropName = "maxLines"

    private fun setExpandableRecipeTV() {
        val recipeTV : TextView = binding.recipeTextView
        val expandFab = binding.expandTextFab

        recipeTV.post {
            if (recipeTV.lineCount > recipeMaxLines) {
                expandFab.visibility = VISIBLE

                recipeTV.viewTreeObserver.addOnGlobalLayoutListener {
                    if (expandable) {
                        expandable = false
                        if (recipeTV.lineCount > recipeMaxLines) {
                            val animation = ObjectAnimator.ofInt(recipeTV, maxLinesPropName, recipeMaxLines)
                            animation.setDuration(0).start()
                            expandFab.show()
                        } else {
                            expandFab.hide()
                        }
                    }
                }

                fun expandOrCollapseText() {
                    if (!expand) {
                        expand = true
                        expandFab.hide()
                        val animation = ObjectAnimator.ofInt(recipeTV, maxLinesPropName, recipeTV.lineCount)
                        animation.setDuration(150).start()
                    } else {
                        expandFab.show()
                        expand = false
                        val animation = ObjectAnimator.ofInt(recipeTV, maxLinesPropName, recipeMaxLines)
                        animation.setDuration(150).start()
                    }
                }

                recipeTV.setOnClickListener {
                    expandOrCollapseText()
                }
                expandFab.setOnClickListener {
                    expandOrCollapseText()
                }
            } else {
                expandFab.visibility = GONE
            }
        }
    }

    private fun setRecyclerView() {
        val recyclerView = binding.mealIngredientsRecyclerGridView
        val orientation = requireContext().resources.configuration.orientation

        val manager: RecyclerView.LayoutManager
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            manager = GridLayoutManager(
                context, ItemViewHelper.calculateNumbOfColumns(requireContext()),
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
        ingredientsAdapter = IngredientsAdapter(requireContext(), ingredientManager)
        recyclerView.adapter = ingredientsAdapter
    }

    private fun observeData() {
        binding.progressbar.visibility = VISIBLE
        mealViewModel.provideMealsLD().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                val meal = it.first()
                inflateViews(meal)
                setLikeImageButton(meal.isFav)
            }
        })
    }

    private fun inflateViews(meal: Meal) {
        binding.favImageButton.visibility = VISIBLE
        ImageViewHelper.loadToImageView(binding.mealImageView, meal.urlImage)

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = meal.name

        if (!meal.instruction.isNullOrBlank()) {
            binding.recipeTextView.text = meal.instruction
            binding.divider.visibility = VISIBLE
            binding.progressbar.visibility = GONE
        }

        setExpandableRecipeTV()

        val ingredientList = RecipeFactory.buildIngredientList(meal)
        ingredientsAdapter.refreshData(ingredientList)
    }

    override fun inject(fragmentsSubcomponent: FragmentsSubcomponent) {
        fragmentsSubcomponent.inject(this)
    }

    override fun disposeBinding() {
        _binding = null
    }
}