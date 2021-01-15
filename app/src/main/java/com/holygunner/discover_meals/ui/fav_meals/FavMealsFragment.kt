package com.holygunner.discover_meals.ui.fav_meals

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.holygunner.discover_meals.R
import com.holygunner.discover_meals.databinding.FavouriteMealsLayoutBinding
import com.holygunner.discover_meals.di.FragmentsSubcomponent
import com.holygunner.discover_meals.interfaces.ItemSelectable
import com.holygunner.discover_meals.models.Meal
import com.holygunner.discover_meals.repository.DbWrapper
import com.holygunner.discover_meals.repository.ISaver
import com.holygunner.discover_meals.ui.BaseFragment
import com.holygunner.discover_meals.ui.MealsAdapter
import org.jetbrains.annotations.Contract
import javax.inject.Inject

class FavMealsFragment : BaseFragment() {
    private var _binding : FavouriteMealsLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FavMealsViewModel
    private lateinit var adapter : MealsAdapter

    @Inject
    lateinit var dbWrapper: DbWrapper

    @Inject
    lateinit var saver: ISaver

    override fun inject(fragmentsSubcomponent: FragmentsSubcomponent) {
        fragmentsSubcomponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavMealsViewModel::class.java)
        _binding = FavouriteMealsLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        setupRecyclerView()
        setSwipeToRemove()
        return view
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerViewRoot.mealsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val favs = saver.getAllFavMealIds()
        adapter = MealsAdapter(requireActivity() as ItemSelectable<Meal>)
        recyclerView.adapter = adapter

        viewModel.provideFavMealsLD().observe(viewLifecycleOwner, {
            if (delMealPosition >= 0) {
                adapter.onItemRemoved(delMealPosition)
            }
            adapter.refreshData(it)
        })
    }

    private var delMealPosition : Int = -1

    private fun setSwipeToRemove() {
        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                var xMark: Drawable? = null
                var xMarkMargin = 0
                var initiated = false
                private fun init() {
                    xMark = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_clear
                    )
                    xMarkMargin = context!!.resources.getDimension(R.dimen.ic_clear_margin).toInt()
                    initiated = true
                }

                @Contract(pure = true)
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                        val position = viewHolder.adapterPosition
                        val meal = adapter.positionToMeal(position)
                        delMealPosition = position
                        viewModel.removeMealFromFavs(meal)
                    }
                }

                override fun onChildDraw(
                    c: Canvas, recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float, dY: Float, actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    if (viewHolder.adapterPosition == -1) {
                        return
                    }
                    if (!initiated) {
                        init()
                    }

                    // draw x mark
                    val itemHeight = itemView.bottom - itemView.top
                    val intrinsicWidth = xMark!!.intrinsicWidth
                    val intrinsicHeight = xMark!!.intrinsicWidth
                    val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
                    val xMarkRight = itemView.right - xMarkMargin
                    val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                    val xMarkBottom = xMarkTop + intrinsicHeight
                    xMark!!.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)
                    xMark!!.draw(c)
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewRoot.mealsRecyclerView)
    }

    override fun disposeBinding() {
        _binding = null
    }
}