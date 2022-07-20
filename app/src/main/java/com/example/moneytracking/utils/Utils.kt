package com.example.moneytracking.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import com.example.moneytracking.MainActivity
import com.example.moneytracking.database.CategorySum
import com.example.moneytracking.databinding.FragmentHomeBinding
import com.example.moneytracking.databinding.FragmentMoneyTrackerBinding
import com.example.moneytracking.ui.moneytrack.MoneyTrackerViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


fun View.hideKeyboard() {
	val inputManager =
		context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputManager.hideSoftInputFromWindow(windowToken, 0)
}


fun getPieDiagram(
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
		pieChartSum.setNoDataText("Нет данных за текущий период")
		pieChartSum.invalidate()
	} else {
		val pieChartSum = binding.piechart
		pieChartSum.clear()
		Toast.makeText(context, "Нет данных", Toast.LENGTH_LONG).show()
	}
}









