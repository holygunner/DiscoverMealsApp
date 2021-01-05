package com.achyzh.discovermeals2020.ui.meals_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.achyzh.discovermeals2020.values.Keys
import com.achyzh.discovermeals2020.ui.BaseFragment
import com.achyzh.discovermeals2020.databinding.FragmentMealsResultBinding
import com.achyzh.discovermeals2020.di.FragmentsSubcomponent
import com.achyzh.discovermeals2020.interfaces.ItemSelectable
import com.achyzh.discovermeals2020.models.Meal
import com.achyzh.discovermeals2020.repository.ISaver
import com.achyzh.discovermeals2020.ui.MealsAdapterKt
import javax.inject.Inject

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
        val ingredients = arguments?.getStringArray(Keys.INGREDIENT_NAMES)
        ingredients?.let { viewModel.loadData(it) }
        val view = binding.root
        setupRecyclerView()
        observeProgress()
        return view
    }

    lateinit var adapter: MealsAdapterKt

    @Suppress("UNCHECKED_CAST")
    private fun setupRecyclerView() {
        val recyclerView = binding.mealsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MealsAdapterKt(requireActivity() as ItemSelectable<Meal>, viewModel.getFavMeals())
        recyclerView.adapter = adapter
        viewModel.mealsLiveData.observe(viewLifecycleOwner, { meals ->
            adapter.refreshData(meals)
        })
    }

    private fun observeProgress() {
        val progressView = binding.progressView
        viewModel.progressLiveData.observe(viewLifecycleOwner, { isOnProgress ->
            if (isOnProgress)
                progressView.visibility = View.VISIBLE
            else
                progressView.visibility = View.GONE
        })
    }

    override fun inject(fragmentsSubcomponent: FragmentsSubcomponent) {
        fragmentsSubcomponent.inject(this)
    }

    override fun disposeBinding() {
        _binding = null
    }
}