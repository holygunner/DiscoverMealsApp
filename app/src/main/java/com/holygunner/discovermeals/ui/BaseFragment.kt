package com.holygunner.discovermeals.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.holygunner.discovermeals.App
import com.holygunner.discovermeals.di.FragmentsSubcomponent
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragmentsSubcomponent = (context.applicationContext as App)
            .appComponent.fragmentSubcomponent()
            .create()
        inject(fragmentsSubcomponent)
    }

    abstract fun inject(fragmentsSubcomponent: FragmentsSubcomponent)

    override fun onDestroyView() {
        super.onDestroyView()
        disposeBinding()
    }

    abstract fun disposeBinding()

    fun hideKeyboardFrom() {
        val view = requireActivity().currentFocus
        view?.let { v ->
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}