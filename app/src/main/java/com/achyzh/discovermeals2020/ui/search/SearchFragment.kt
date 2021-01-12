package com.achyzh.discovermeals2020.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.achyzh.discovermeals2020.R
import com.achyzh.discovermeals2020.ui.BaseFragment
import com.achyzh.discovermeals2020.databinding.FragmentSearchMealsBinding
import com.achyzh.discovermeals2020.di.FragmentsSubcomponent
import com.achyzh.discovermeals2020.interfaces.ItemSelectable
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.repository.ISaver
import com.achyzh.discovermeals2020.ui.MealsAdapter
import com.achyzh.discovermeals2020.ui.tools.DebouncingQueryTextListener
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