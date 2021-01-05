package com.achyzh.discovermeals2020.ui.meal_recipe

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.achyzh.discovermeals2020.values.Keys.Companion.MEAL_PARCEL_KEY
import com.achyzh.discovermeals2020.R
import com.achyzh.discovermeals2020.business_logic.IngredientManagerKt
import com.achyzh.discovermeals2020.ui.BaseFragment
import com.achyzh.discovermeals2020.databinding.FragmentMealRecipeBinding
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.business_logic.RecipeFactory
import com.achyzh.discovermeals2020.di.FragmentsSubcomponent
import com.achyzh.discovermeals2020.ui.tools.ImageViewHelper
import com.achyzh.discovermeals2020.ui.tools.ItemViewHelper
import javax.inject.Inject

class MealRecipeFragment : BaseFragment() {
    private var _binding : FragmentMealRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mealViewModel: MealRecipeViewModel

    lateinit var ingredientsAdapter: IngredientsAdapter

    @Inject
    lateinit var ingredientManager: IngredientManagerKt

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mealViewModel = ViewModelProvider(this, viewModelFactory).get(MealRecipeViewModel::class.java)
        _binding = FragmentMealRecipeBinding.inflate(inflater, container, false)
        val view = binding.root
        // TODO set views
        val meal : Meal? = arguments?.getParcelable(MEAL_PARCEL_KEY)
        mealViewModel.loadData(meal!!)
        mealViewModel.requestIsMealFav(meal)
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

    private fun setRecyclerView() {
        val recyclerView = binding.mealIngredientsRecyclerGridView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), calcSpanCount())
        ingredientsAdapter = IngredientsAdapter(requireContext(), ingredientManager)
        recyclerView.adapter = ingredientsAdapter
    }

    private fun observeData() {
        mealViewModel.mealLD.observe(viewLifecycleOwner, {
            inflateViews(it)
        })
        mealViewModel.favLD.observe(viewLifecycleOwner, {
            setLikeImageButton(it)
        })
    }

    private fun inflateViews(meal: Meal) {
        binding.recipeCardView.visibility = VISIBLE
        binding.favImageButton.visibility = VISIBLE
        ImageViewHelper.loadToImageView(binding.mealImageView, meal.urlImage)
        binding.mealNameTextView.text = meal.name
        binding.recipeTextView.text = meal.instruction

        val ingredientList = RecipeFactory.buildIngredientList(meal)
        if (ingredientList.isNotEmpty())
            binding.ingredientsListCardView.visibility = VISIBLE
        ingredientsAdapter.refreshData(ingredientList)
    }

    private fun calcSpanCount(): Int {
        val orientation = resources.configuration.orientation
        var spanCount = 2
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = ItemViewHelper
                .calculateNumbOfColumns(requireContext())
        }
        return spanCount
    }

    override fun inject(fragmentsSubcomponent: FragmentsSubcomponent) {
        fragmentsSubcomponent.inject(this)
    }

    override fun disposeBinding() {
        _binding = null
    }
}