package com.holygunner.discover_meals.ui.selected_ingredients

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.holygunner.discover_meals.ui.BaseFragment
import com.holygunner.discover_meals.databinding.ChosenIngredientsLayoutBinding
import com.holygunner.discover_meals.di.FragmentsSubcomponent
import com.holygunner.discover_meals.interfaces.ItemSelectable
import com.holygunner.discover_meals.models.Ingredient
import com.holygunner.discover_meals.ui.tools.ItemViewHelper

class SelectedIngredientsFragment : BaseFragment(), ItemSelectable<Ingredient> {
    private var _binding : ChosenIngredientsLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SelectedIngredientsViewModel
    private lateinit var adapter: SelectedIngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SelectedIngredientsViewModel::class.java)
        _binding = ChosenIngredientsLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        setupRecyclerView()
        setFabOnClickListener()
        refreshFabVisibility()
        return view
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.chosenIngredientsList
        recyclerView.setHasFixedSize(false)
        val layoutManager = GridLayoutManager(requireContext(),
            ItemViewHelper.calculateNumbOfColumns(requireContext()))
        recyclerView.layoutManager = layoutManager
        adapter = SelectedIngredientsAdapter(this, viewModel.itemsToDel)
        recyclerView.adapter = adapter
        viewModel.ingredientsLD.observe(viewLifecycleOwner, {
            adapter.refreshDataWithAnimation(it)
        })
    }

    override fun inject(fragmentsSubcomponent: FragmentsSubcomponent) {
        fragmentsSubcomponent.inject(this)
    }

    override fun disposeBinding() {
        _binding = null
    }

    override fun onItemSelected(item: Ingredient) {
        refreshFabVisibility()
    }

    private fun setFabOnClickListener() {
        binding.removeFab.setOnClickListener {
            viewModel.deleteSelected()
            refreshFabVisibility()
        }
    }

    private fun refreshFabVisibility() {
        val size = viewModel.itemsToDel.size
        if (size > 0) {
            binding.removeFab.show()
        } else {
            binding.removeFab.hide()
        }
    }
}