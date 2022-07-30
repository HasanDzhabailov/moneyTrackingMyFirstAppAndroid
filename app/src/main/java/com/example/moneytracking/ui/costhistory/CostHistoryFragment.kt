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
import com.example.moneytracking.utils.*
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_cost_history.*

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
		fun getPeriodHistoryExpenses(startPeriod: Long, endPeriod: Long) {
			costHistoryViewModel.getHistoryExpensesPeriod(startPeriod, endPeriod)
				.observe(viewLifecycleOwner, Observer { expense -> adapter.setData(expense) })
		}

		fun showDataRangePicker() {
			val dateRangePicker =
				MaterialDatePicker
					.Builder.dateRangePicker()
					.setTitleText("Select Date")
					.build()

			dateRangePicker.show(
				activity!!.supportFragmentManager,
				"date_range_picker"
			)

			dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->
				val startDate = dateSelected.first
				val endDate = dateSelected.second

				if (startDate != null && endDate != null) {
					costHistoryViewModel.getHistoryExpensesPeriod(startDate, endDate)
					getPeriodHistoryExpenses(startDate, endDate)
				}
			}
		}
		getPeriodHistoryExpenses(getStartMonth(), getEndMonth())
		binding.chipsGroupPeriod.chipToday.setOnClickListener {
			getPeriodHistoryExpenses(getStartDay(), getEndDay())
		}
		binding.chipsGroupPeriod.chipWeek.setOnClickListener {
			getPeriodHistoryExpenses(getStartWeek(), getEndWeek())
		}
		binding.chipsGroupPeriod.chipMonth.setOnClickListener {
			getPeriodHistoryExpenses(getStartMonth(), getEndMonth())
		}
		binding.chipsGroupPeriod.chipYear.setOnClickListener {
			getPeriodHistoryExpenses(getStartYear(), getEndYear())
		}
		binding.chipsGroupPeriod.chipSelectDate.setOnClickListener {
			showDataRangePicker()
		}
		return binding.root
	}
}