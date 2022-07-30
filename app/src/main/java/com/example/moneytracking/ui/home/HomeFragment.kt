package com.example.moneytracking.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moneytracking.R
import com.example.moneytracking.model.CategorySum
import com.example.moneytracking.database.MoneyTrackDatabase

import com.example.moneytracking.databinding.FragmentHomeBinding
import com.example.moneytracking.utils.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.chips_filter_period.*
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		val binding:FragmentHomeBinding =
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
		fun getExpense(startPeriod: Long, endPeriod: Long, text: String) {
			var sumExpenses = 0L
			homeViewModel.getSumExpenses(startPeriod, endPeriod)
				.observe(viewLifecycleOwner) { sumExpense ->
					sumExpenses = sumExpense ?: 0L

					binding.textTotalAmount.text = "$text $sumExpenses ₽"
				}
			homeViewModel.getSumCategoryExpenses(startPeriod, endPeriod)
				.observe(viewLifecycleOwner) { sumExpense ->
					getPieDiagram(requireContext(), sumExpense, binding)
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
					var strStartDate =
						convertLongToDateString(startDate).dropLast(6).replace('-', '.')
					var strEndDate = convertLongToDateString(endDate).dropLast(6).replace('-', '.')
					homeViewModel.getSumCategoryExpenses(startDate, endDate)
						.observe(viewLifecycleOwner) { sumExpense ->
							getPieDiagram(requireContext(), sumExpense, binding)
						}
					getExpense(startDate,
						endDate,
						"${getString(R.string.total_amount_startPeriod)} $strStartDate\n${
							getString(R.string.total_amount_EndPeriod)
						} $strEndDate:")
				}
			}

		}
		getExpense(getStartMonth(), getEndMonth(), "${getString(R.string.total_amount_month)}")
		binding.chipsGroup.chipToday.setOnClickListener {
			getExpense(getStartDay(), getEndDay(), "${getString(R.string.total_amount_today)}")
		}
		binding.chipsGroup.chipWeek.setOnClickListener {
			getExpense(getStartWeek(), getEndWeek(), "${getString(R.string.total_amount_week)}")
		}
		binding.chipsGroup.chipMonth.setOnClickListener {
			getExpense(getStartMonth(), getEndMonth(), "${getString(R.string.total_amount_month)}")
		}
		binding.chipsGroup. chipYear.setOnClickListener {
			getExpense(getStartYear(), getEndYear(), "${getString(R.string.total_amount_year)}")
		}
		binding.chipsGroup.chipSelectDate.setOnClickListener {
			showDataRangePicker()
		}

		binding.btnCreateExpense.setOnClickListener {
			this.findNavController()
				.navigate(HomeFragmentDirections.actionHomeFragmentToMoneyTrackerFragment())
		}
		return binding.root
	}
	// create PieDiagram
	private fun getPieDiagram(
		context: Context,
		sumExpense: List<CategorySum>,
		binding: FragmentHomeBinding,
	) {
		if (sumExpense.isNotEmpty()) {
			val pieChartSum = binding.piechart
			val entries: MutableList<PieEntry> = ArrayList()
			Collections.sort(entries, EntryXComparator())

			sumExpense.forEach { i ->
				entries.add(PieEntry(i.sumExpense.toFloat(), i.categoryExpense))
			}

			val pieDataSet = PieDataSet(entries, null)

			pieDataSet.setColors(*ColorTemplate.MATERIAL_COLORS + getColorPieChart())
			pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
			pieDataSet.valueTextColor = Color.BLACK
			pieDataSet.valueTextSize = 16F
			val pieData = PieData(pieDataSet)
			pieChartSum.setEntryLabelTextSize(0F)
			pieChartSum.transparentCircleRadius = 55f
			pieChartSum.holeRadius = 55f
			pieChartSum.description.isEnabled = false
			pieChartSum.legend.formSize = 16f
			pieChartSum.legend.textColor = Color.BLACK
			pieChartSum.legend.textSize = 16f
			pieChartSum.legend.xEntrySpace = 3f
			pieChartSum.legend.yEntrySpace = 3f
			pieChartSum.legend.isWordWrapEnabled = true
			pieChartSum.data = pieData
			pieChartSum.setNoDataText("Нет данных за текущий период")
			pieChartSum.invalidate()
		} else {
			val pieChartSum = binding.piechart
			pieChartSum.clear()
			Toast.makeText(context, "Нет данных", Toast.LENGTH_LONG).show()
		}
	}


}