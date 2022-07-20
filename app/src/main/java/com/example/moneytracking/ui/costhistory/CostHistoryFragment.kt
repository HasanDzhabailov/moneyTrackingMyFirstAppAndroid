package com.example.moneytracking.ui.costhistory

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneytracking.R
import com.example.moneytracking.database.MoneyTrackDatabase

import com.example.moneytracking.databinding.FragmentCostHistoryBinding

class CostHistoryFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		val binding: FragmentCostHistoryBinding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_cost_history, container, false)
		val application = requireNotNull(this.activity).application

		// Create an instance of the ViewModel Factory.
		val dataSource = MoneyTrackDatabase.getInstance(application).moneyTrackDatabaseDao
		val viewModelFactory = CostHistoryViewModelFactory(dataSource)
		// Get a reference to the ViewModel associated with this fragment.
		val costHistoryViewModel =
			ViewModelProvider(this, viewModelFactory).get(CostHistoryViewModel::class.java)

		// To use the View Model with data binding, you have to explicitly
		// give the binding object a reference to it.
		binding.costHistoryViewModel = costHistoryViewModel
		// Specify the current activity as the lifecycle owner of the binding.
		// This is necessary so that the binding can observe LiveData updates.
		binding.lifecycleOwner = this
		// Recyclerview
		val adapter = CostHistoryAdapter()
		binding.recyclerViewCostHistory.adapter = adapter
		binding.recyclerViewCostHistory.layoutManager = LinearLayoutManager(requireContext())

		costHistoryViewModel.allExpenses.observe(viewLifecycleOwner, Observer {expense -> adapter.setData(expense)  })

		return binding.root
	}
}