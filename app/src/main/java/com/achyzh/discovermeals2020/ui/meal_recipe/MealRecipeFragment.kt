package com.achyzh.discovermeals2020.ui.meal_recipe

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.achyzh.discovermeals2020.R
import com.achyzh.discovermeals2020.business_logic.IngredientManager
import com.achyzh.discovermeals2020.business_logic.RecipeFactory
import com.achyzh.discovermeals2020.databinding.FragmentMealRecipeV2Binding
import com.achyzh.discovermeals2020.di.FragmentsSubcomponent
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.ui.BaseFragment
import com.achyzh.discovermeals2020.ui.tools.ImageViewHelper
import com.achyzh.discovermeals2020.ui.tools.ItemViewHelper
import com.achyzh.discovermeals2020.values.Keys.Companion.MEAL_PARCEL_KEY
import javax.inject.Inject


class MealRecipeFragment : BaseFragment() {
    private var _binding : FragmentMealRecipeV2Binding? = null
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
        _binding = FragmentMealRecipeV2Binding.inflate(inflater, container, false)
        val view = binding.root
        val meal : Meal? = arguments?.getParcelable(MEAL_PARCEL_KEY)
        mealViewModel.setMealId(meal!!.id)
        mealViewModel.loadData(meal)
        setRecyclerView()
        observeData()
        setFavView()
        setExpandableRecipeTV()
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
    private val recipeCollapsedMaxLines = 16

    private fun setExpandableRecipeTV() {
        val recipeTV = binding.recipeTextView
        recipeTV.viewTreeObserver.addOnGlobalLayoutListener {
            if (expandable) {
                expandable = false
                if (recipeTV.lineCount > 8) {
                    val animation = ObjectAnimator.ofInt(recipeTV, "maxLines", recipeCollapsedMaxLines)
                    animation.setDuration(0).start()
                }
            }
        }

        recipeTV.setOnClickListener {
            if (!expand) {
                expand = true
                val animation = ObjectAnimator.ofInt(recipeTV, "maxLines", recipeTV.lineCount)
                animation.setDuration(150).start()
            } else {
                expand = false
                val animation = ObjectAnimator.ofInt(recipeTV, "maxLines", recipeCollapsedMaxLines)
                animation.setDuration(150).start()
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
        binding.mealNameTextView.text = meal.name

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = meal.name

        binding.recipeTextView.text = meal.instruction

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