package com.example.moneytracking.ui

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.*
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.databinding.FragmentMoneyTrackerBinding
import com.example.moneytracking.viewmodels.moneytrack.MoneyTrackerViewModel
import com.example.moneytracking.viewmodels.moneytrack.MoneyTrackerViewModelFactory
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*
import kotlin.collections.ArrayList


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

		var toDaySumExpenseGl = 0L
		moneyTrackViewModel.sumExpenseToDay.observe(viewLifecycleOwner) { sumExpense ->
			toDaySumExpenseGl = sumExpense ?: 0L
			binding.textAllSum.text = "Расходы за текущий день: $toDaySumExpenseGl ₽"
		}
		fun getToAllExpense() {

			moneyTrackViewModel.sumExpense.observe(viewLifecycleOwner) { sumExpense ->
				if(sumExpense.isNotEmpty()){
				val pieChartSum = binding.piechart
				val entries: MutableList<PieEntry> = ArrayList()
				Collections.sort(entries, EntryXComparator())

				sumExpense.forEach { i ->
					entries.add(PieEntry(i.sumExpense.toFloat(), i.categoryExpense))
				}

				val pieDataSet = PieDataSet(entries, null)
				pieDataSet.setColors(*ColorTemplate.COLORFUL_COLORS + ColorTemplate.MATERIAL_COLORS + ColorTemplate.JOYFUL_COLORS)
				pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
				pieDataSet.valueLinePart1OffsetPercentage = 15f
				pieDataSet.valueLinePart1Length = 0.1f
				pieDataSet.valueLinePart2Length = 0.1f
				pieDataSet.valueTextColor = Color.BLACK
				pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
				val pieData = PieData(pieDataSet)
				pieData.setValueTextSize(14f)
				pieData.setValueTextColor(Color.BLACK)
					pieChartSum.setEntryLabelColor(Color.BLACK)
//					pieChartSum.isDrawHoleEnabled = false
					pieChartSum.transparentCircleRadius = 55f
					pieChartSum.holeRadius = 55f
					pieChartSum.setCenterTextSize(5f)
					pieChartSum.setDrawCenterText(true)
					pieChartSum.description.isEnabled = false
					pieChartSum.legend.formSize = 16f
					pieChartSum.legend.textColor = Color.BLACK
					pieChartSum.legend.textSize = 16f
//					pieChartSum.legend.form = Legend.LegendForm.CIRCLE
					pieChartSum.legend.xEntrySpace = 3f
					pieChartSum.legend.yEntrySpace = 3f
					pieChartSum.legend.isWordWrapEnabled = true
					pieChartSum.data = pieData
					pieChartSum.invalidate()
				}
				else{
					Toast.makeText(requireContext(),"Нет данных", Toast.LENGTH_LONG).show()
				}
			}
		}

		fun getToDayExpense(){
			var toDaySumExpense = 0L
			moneyTrackViewModel.sumExpenseToDay.observe(viewLifecycleOwner) { sumExpense ->
				toDaySumExpense = sumExpense ?: 0L
				binding.textAllSum.text = "Расходы за текущий день: $toDaySumExpense ₽"
			}
		}
		fun getToWeekExpense(){
			var toWeekSumExpense = 0L
			moneyTrackViewModel.sumExpenseToWeek.observe(viewLifecycleOwner) { sumExpense ->
				toWeekSumExpense = sumExpense ?: 0L
				binding.textAllSum.text = "Расходы за неделю: $toWeekSumExpense ₽"
			}
		}
		fun getToMonthExpense(){
			var toMonthSumExpense = 0L
			moneyTrackViewModel.sumExpenseToMonth.observe(viewLifecycleOwner) { sumExpense ->
				toMonthSumExpense = sumExpense ?: 0L
				binding.textAllSum.text = "Расходы за месяц: $toMonthSumExpense ₽"
			}
		}
		fun getToYearExpense(){
			var toYearSumExpense = 0L
			moneyTrackViewModel.sumExpenseToYear.observe(viewLifecycleOwner) { sumExpense ->
				toYearSumExpense = sumExpense ?: 0L
				binding.textAllSum.text = "Расходы за год: $toYearSumExpense ₽"
			}
		}

		binding.btnSelectionToDay.setOnClickListener{
			getToDayExpense()
		}
		binding.btnSelectionToWeek.setOnClickListener{
			getToWeekExpense()
		}
		binding.btnSelectionToMonth.setOnClickListener{
			getToMonthExpense()
		}
		binding.btnSelectionToYear.setOnClickListener{
			getToYearExpense()
		}
		binding.btnAllSumExpense.setOnClickListener {
			getToAllExpense()
		}





		return binding.root
	}


}
