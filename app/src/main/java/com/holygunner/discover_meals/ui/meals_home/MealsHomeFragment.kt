package com.holygunner.discover_meals.ui.meals_home

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.holygunner.discover_meals.values.Keys
import com.holygunner.discover_meals.R
import com.holygunner.discover_meals.ui.BaseFragment
import com.holygunner.discover_meals.databinding.FragmentMealsHomeBinding
import com.holygunner.discover_meals.di.FragmentsSubcomponent
import com.holygunner.discover_meals.interfaces.ItemSelectable
import com.holygunner.discover_meals.models.Ingredient
import com.google.android.material.snackbar.Snackbar

class MealsHomeFragment :
    BaseFragment(),
    ItemSelectable<Ingredient> {
    private var _binding: FragmentMealsHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: MealsHomeViewModel
    private lateinit var adapter: AdapterCategories

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(MealsHomeViewModel::class.java)
        _binding = FragmentMealsHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        setFab()
        setupRecyclerView()
        refreshFiledItems()
        return view
    }

    private fun refreshFiledItems() {
//        homeViewModel.refreshFiledIngredients()
//        homeViewModel.dataSetChangedLD.observe(viewLifecycleOwner, {
//            adapter.notifyDataSetChanged()
//        })
    }

    override fun inject(fragmentsSubcomponent: FragmentsSubcomponent) {
        fragmentsSubcomponent.inject(this)
    }

    override fun disposeBinding() {
        _binding = null
    }

    override fun onPause() {
//        homeViewModel.storeSelectedIngredients(adapter.getBackCategories())
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun setFab() {
        binding.fab.setOnClickListener {
            val result = goToMealsResultScreen()
            if (!result) {
                Snackbar.make(it, R.string.no_chosen_ingredients, Snackbar.LENGTH_LONG).show()
            }

        }
    }

    private fun goToMealsResultScreen() : Boolean {
//        val categories = adapter.getBackCategories()
//        val selectedIngredients: MutableList<Ingredient>
//        selectedIngredients = mutableListOf<Ingredient>()
//        for (category in categories) {
//            for (ingr in category.ingredients) {
//                if (ingr.isFill)
//                    selectedIngredients.add(ingr)
//            }
//        }

        val selectedIngredients = homeViewModel.getSelectedIngredients()

        if (selectedIngredients.isEmpty())
            return false

//        val ingrNames = mutableListOf<String>()
//        for (ingr in selectedIngredients) {
//            ingr.name?.let { ingrNames.add(it) }
//        }

//        val bundle = bundleOf(Keys.INGREDIENT_NAMES to ingrNames.toTypedArray())
        val bundle = bundleOf(Keys.SELECTED_INGREDIENTS to selectedIngredients)
        findNavController()
            .navigate(R.id.nav_to_meals_result_fragment, bundle)
        return true
    }

    private fun setupRecyclerView() {
        binding.recyclerview.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerview.layoutManager = layoutManager
        adapter = AdapterCategories(this as ItemSelectable<Ingredient>)
        binding.recyclerview.adapter = adapter
//        homeViewModel.categoriesLiveData.observe(viewLifecycleOwner, {
//            homeViewModel.refreshFilledIngredients()
//            adapter.overrideData(it)
//        })

//        homeViewModel.categoriesLiveData
//            .map {
//                homeViewModel.refreshFilledIngredients()
//                it
//            }
//            .observe(viewLifecycleOwner, {
//                adapter.overrideData(it)
//        })

        homeViewModel.observeCategoriesLiveData()
            .observe(viewLifecycleOwner, {
                adapter.overrideData(it)
            })
    }

    override fun onItemSelected(item: Ingredient) {
        homeViewModel.storeSelectedIngredients(adapter.getBackCategories())
    }
}