package com.holygunner.discover_meals.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.holygunner.discover_meals.R
import com.holygunner.discover_meals.ui.BaseFragment
import com.holygunner.discover_meals.databinding.FragmentSearchMealsBinding
import com.holygunner.discover_meals.di.FragmentsSubcomponent
import com.holygunner.discover_meals.interfaces.ItemSelectable
import com.holygunner.discover_meals.models.Meal
import com.holygunner.discover_meals.repository.ISaver
import com.holygunner.discover_meals.ui.MealsAdapter
import com.holygunner.discover_meals.ui.tools.DebouncingQueryTextListener
import javax.inject.Inject

class SearchFragment : BaseFragment() {
    private var _binding : FragmentSearchMealsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var saver: ISaver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        _binding = FragmentSearchMealsBinding.inflate(inflater, container, false)
        val view = binding.root
        setSearchView()
        setupRecyclerView()
        observeFavMeals()
        return view
    }

    private fun observeFavMeals() {
        viewModel.provideFavsLD().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                val favSet = hashSetOf<Meal>()
                favSet.addAll(it)
                viewModel.favsMeals.postValue(favSet)
            }
        })
    }

    private fun setSearchView() {
        val searchView = binding.searchBar
        searchView.queryHint = getString(R.string.enter_meal_name)
        searchView.clipToPadding = true

        searchView.setOnQueryTextListener(
            DebouncingQueryTextListener(
                lifecycle
            ) { newText ->
                newText?.let {
                    if (it.isEmpty()) {
                        viewModel.resetSearch()
                    } else {
                        viewModel.searchData(it)
                    }
                }
            }
        )
    }

    private fun invokeSearch(query: String) {
        viewModel.searchData(query)
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerview
        val adapter = MealsAdapter(requireActivity() as ItemSelectable<Meal>)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.mealsLiveData.observe(viewLifecycleOwner, {
            adapter.refreshData(it)
        })
    }

    override fun inject(fragmentsSubcomponent: FragmentsSubcomponent) {
        fragmentsSubcomponent.inject(this)
    }

    override fun onDestroyView() {
        super.hideKeyboardFrom()
        super.onDestroyView()
    }

    override fun disposeBinding() {
        _binding = null
    }
}