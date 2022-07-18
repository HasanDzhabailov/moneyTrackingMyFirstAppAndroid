package com.example.moneytracking.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.*
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.databinding.FragmentMoneyTrackerBinding
import com.example.moneytracking.viewmodels.moneytrack.MoneyTrackerViewModel
import com.example.moneytracking.viewmodels.moneytrack.MoneyTrackerViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_money_tracker.*
import java.time.Year


class MoneyTrackerFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		// Inflate the layout for this fragment
		val binding: FragmentMoneyTrackerBinding =
			DataBindingUtil.inflate(inflater, R.layout.fragment_money_tracker, container, false)

		val application = requireNotNull(this.activity).application

		// Create an instance of the ViewModel Factory.
		val dataSource = MoneyTrackDatabase.getInstance(application).moneyTrackDatabaseDao
		val viewModelFactory = MoneyTrackerViewModelFactory(dataSource, application)

		// Create an drop down menu.
		val categoryExpenses = resources.getStringArray(R.array.expenses)
		val adapter =
			ArrayAdapter(requireContext(), R.layout.list_item_category_expensive, categoryExpenses)
		binding.AutoCompleteTextView.setAdapter(adapter)

		// Get a reference to the ViewModel associated with this fragment.
		val moneyTrackViewModel =
			ViewModelProvider(this, viewModelFactory).get(MoneyTrackerViewModel::class.java)

		// To use the View Model with data binding, you have to explicitly
		// give the binding object a reference to it.
		binding.moneyTrackViewModel = moneyTrackViewModel

		// Specify the current activity as the lifecycle owner of the binding.
		// This is necessary so that the binding can observe LiveData updates.
		binding.lifecycleOwner = this
		binding.piechart.setNoDataText("Нет данных за текущий период")

		binding.btnAddExpenses.setOnClickListener {
			var categoryExpensesString = binding.menu.editText!!.text.toString()
			var sumExpenseString = binding.textSumExpense.text.toString()

			if (TextUtils.isEmpty(categoryExpensesString)) {
				binding.menu.error = "Заполните поле"
			} else if (TextUtils.isEmpty(sumExpenseString) || sumExpenseString == "0") {
				binding.TextFieldSumExpense.error = "Заполните поле"
			} else {
				binding.menu.isErrorEnabled = false
				binding.TextFieldSumExpense.isErrorEnabled = false

				moneyTrackViewModel.addExpenses(categoryExpensesString, sumExpenseString.toLong())

				binding.menu.editText?.text?.clear()
				binding.textSumExpense.text?.clear()

				binding.menu.clearFocus()
				binding.TextFieldSumExpense.clearFocus()
			}

			it.hideKeyboard()
		}






		fun getExpense(startPeriod:Long, endPeriod:Long, text: String){
			var sumExpenses = 0L
			moneyTrackViewModel.getSumExpenses(startPeriod, endPeriod).observe(viewLifecycleOwner) { sumExpense ->
				sumExpenses = sumExpense ?: 0L

				binding.textAllSum.text = "$text $sumExpenses ₽"
			}
			moneyTrackViewModel.getSumCategoryExpenses(startPeriod,endPeriod).observe(viewLifecycleOwner){
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
					moneyTrackViewModel.getSumCategoryExpenses(startDate,endDate).observe(viewLifecycleOwner){
							sumExpense ->
						getPieDiagram(requireContext(),sumExpense,binding)
					}
					getExpense(startDate,endDate,"От $strStartDate по $strEndDate: ")
				}
			}

		}

		getExpense(getStartMonth(),getEndMonth(), "Итог за месяц: ")
		binding.btnSelectionToDay.setOnClickListener{
			getExpense(getStartDay(), getEndDay(), "Итог за день: ")
		}
		binding.btnSelectionToWeek.setOnClickListener{
			getExpense(getStartWeek(), getEndWeek(), "Итог за неделю: ")
		}
		binding.btnSelectionToMonth.setOnClickListener{
			getExpense(getStartMonth(), getEndMonth(), "Итог за месяц: ")
		}
		binding.btnSelectionToYear.setOnClickListener{
			getExpense(getStartYear(), getEndYear(), "Итог за год: ")
		}
		binding.btnAllSumExpense.setOnClickListener {
			showDataRangePicker()
		}





		return binding.root
	}



}
