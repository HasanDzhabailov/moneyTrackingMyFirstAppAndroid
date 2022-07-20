package com.example.moneytracking.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moneytracking.R
import com.example.moneytracking.database.MoneyTrackDatabase

import com.example.moneytracking.databinding.FragmentHomeBinding
import com.example.moneytracking.utils.*
import com.google.android.material.datepicker.MaterialDatePicker


class HomeFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		val binding: FragmentHomeBinding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
		val application = requireNotNull(this.activity).application

		// Create an instance of the ViewModel Factory.
		val dataSource = MoneyTrackDatabase.getInstance(application).moneyTrackDatabaseDao
		val viewModelFactory = HomeViewModelFactory(dataSource)
		val homeViewModel =
			ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
		binding.homeViewModel = homeViewModel
		binding.lifecycleOwner = this
		binding.piechart.setNoDataText("Нет данных за текущий период")
		fun getExpense(startPeriod:Long, endPeriod:Long, text: String){
			var sumExpenses = 0L
			homeViewModel.getSumExpenses(startPeriod, endPeriod).observe(viewLifecycleOwner) { sumExpense ->
				sumExpenses = sumExpense ?: 0L

				binding.textTotalAmount.text = "$text $sumExpenses ₽"
			}
			homeViewModel.getSumCategoryExpenses(startPeriod,endPeriod).observe(viewLifecycleOwner){
					sumExpense ->
				getPieDiagram(requireContext(),sumExpense,binding)
			}
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
					var strStartDate = convertLongToDateString(startDate).dropLast(6).replace('-','.')
					var strEndDate = convertLongToDateString(endDate).dropLast(6).replace('-','.')
					homeViewModel.getSumCategoryExpenses(startDate,endDate).observe(viewLifecycleOwner){
							sumExpense ->
						getPieDiagram(requireContext(),sumExpense,binding)
					}
					getExpense(startDate,endDate,"${getString(R.string.total_amount_startPeriod)} $strStartDate\n${getString(R.string.total_amount_EndPeriod)} $strEndDate:")
				}
			}

		}
		getExpense(getStartMonth(), getEndMonth(), "${getString(R.string.total_amount_month)}")
		binding.chipToday.setOnClickListener{
			getExpense(getStartDay(), getEndDay(), "${getString(R.string.total_amount_today)}")
		}
		binding.chipWeek.setOnClickListener{
			getExpense(getStartWeek(), getEndWeek(), "${getString(R.string.total_amount_week)}")
		}
		binding.chipMonth.setOnClickListener{
			getExpense(getStartMonth(), getEndMonth(), "${getString(R.string.total_amount_month)}")
		}
		binding.chipYear.setOnClickListener{
			getExpense(getStartYear(), getEndYear(), "${getString(R.string.total_amount_year)}")
		}
		binding.chipSelectDate.setOnClickListener{
			showDataRangePicker()
		}

		binding.btnCreateExpense.setOnClickListener {
			this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMoneyTrackerFragment())
		}
		return binding.root
	}


}