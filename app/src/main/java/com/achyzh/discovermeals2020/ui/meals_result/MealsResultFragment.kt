package com.achyzh.discovermeals2020.ui.meals_result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.achyzh.discovermeals2020.values.Keys
import com.achyzh.discovermeals2020.ui.BaseFragment
import com.achyzh.discovermeals2020.databinding.FragmentMealsResultBinding
import com.achyzh.discovermeals2020.di.FragmentsSubcomponent
import com.achyzh.discovermeals2020.interfaces.ItemSelectable
import com.achyzh.discovermeals2020.models.Ingredient
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.ui.MealsAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MealsResultFragment : BaseFragment() {
    private var _binding : FragmentMealsResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MealsResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MealsResultViewModel::class.java)
        _binding = FragmentMealsResultBinding.inflate(inflater, container, false)
        val ingredients = arguments?.getParcelableArray(Keys.SELECTED_INGREDIENTS) as Array<Ingredient>
        ingredients.let {
            viewModel.loadData(it)
        }
        val view = binding.root
        setupRecyclerView()
        observeProgress()
        return view
    }

    lateinit var adapter: MealsAdapter

    @Suppress("UNCHECKED_CAST")
    private fun setupRecyclerView() {
        val recyclerView = binding.mealsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MealsAdapter(requireActivity() as ItemSelectable<Meal>)
        recyclerView.adapter = adapter
        viewModel.mealsLiveData.observe(viewLifecycleOwner, { meals ->
//            lifecycleScope.launch {
//                delay(2000)
//            }

            if (!(viewModel.isDataLoadAlready())) {
                adapter.refreshData(meals)
            }
        })
    }

    private fun observeProgress() {
        val shimmer = binding.shimmerViewContainer
        viewModel.progressLiveData.observe(viewLifecycleOwner, { isOnProgress ->
            if (isOnProgress) {
                shimmer.startShimmer()
            } else {
                shimmer.hideShimmer()
                shimmer.visibility = View.GONE
            }
        })
    }

    override fun inject(fragmentsSubcomponent: FragmentsSubcomponent) {
        fragmentsSubcomponent.inject(this)
    }

    override fun disposeBinding() {
        _binding = null
    }
}