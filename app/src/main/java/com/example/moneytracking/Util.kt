package com.example.moneytracking

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.moneytracking.database.Category_sum
import com.example.moneytracking.databinding.FragmentMoneyTrackerBinding
import com.example.moneytracking.viewmodels.moneytrack.MoneyTrackerViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
	return SimpleDateFormat("MM-dd-yyyy HH:mm")
		.format(systemTime).toString()
}


fun View.hideKeyboard() {
	val inputManager =
		context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun getStartDay(): Long {
	val c = Calendar.getInstance()
	c.add(Calendar.DAY_OF_MONTH, 0)
	c[Calendar.HOUR_OF_DAY] = 0
	c[Calendar.MINUTE] = 0
	c[Calendar.SECOND] = 0
	c[Calendar.MILLISECOND] = 0
	return c.timeInMillis
}

fun getEndDay(): Long {
	val c = Calendar.getInstance()
	c.add(Calendar.DAY_OF_MONTH, 1)
	c[Calendar.HOUR_OF_DAY] = 0
	c[Calendar.MINUTE] = 0
	c[Calendar.SECOND] = 0
	c[Calendar.MILLISECOND] = 0
	return c.timeInMillis - 1
}

fun getStartWeek(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_WEEK,c.firstDayOfWeek)
	c[Calendar.HOUR_OF_DAY] = 0
	c[Calendar.MINUTE] = 0
	c[Calendar.SECOND] = 0
	c[Calendar.MILLISECOND] = 0
	return c.timeInMillis
}

fun getEndWeek(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_WEEK,8)
	c[Calendar.HOUR_OF_DAY] = 23
	c[Calendar.MINUTE] = 59
	c[Calendar.SECOND] = 59
	c[Calendar.MILLISECOND] = 999
	return c.timeInMillis
}

fun getStartMonth(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH) )
	c[Calendar.HOUR_OF_DAY] = 0
	c[Calendar.MINUTE] = 0
	c[Calendar.SECOND] = 0
	c[Calendar.MILLISECOND] = 0
	return c.timeInMillis
}
fun getEndMonth(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH) )
	c[Calendar.HOUR_OF_DAY] = 23
	c[Calendar.MINUTE] = 59
	c[Calendar.SECOND] = 59
	c[Calendar.MILLISECOND] = 999
	return c.timeInMillis
}
fun getStartYear(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR) )
	c[Calendar.HOUR_OF_DAY] = 0
	c[Calendar.MINUTE] = 0
	c[Calendar.SECOND] = 0
	c[Calendar.MILLISECOND] = 0
	return c.timeInMillis
}
fun getEndYear(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR) )
	c[Calendar.HOUR_OF_DAY] = 23
	c[Calendar.MINUTE] = 59
	c[Calendar.SECOND] = 59
	c[Calendar.MILLISECOND] = 999
	return c.timeInMillis
}

fun getPieDiagram(context: Context,sumExpense: List<Category_sum>,binding:FragmentMoneyTrackerBinding){
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
		pieChartSum.setNoDataText("Нет данных за текущий период")
		pieChartSum.invalidate()
	}
	else{
		val pieChartSum = binding.piechart
		pieChartSum.clear()
		Toast.makeText(context,"Нет данных", Toast.LENGTH_LONG).show()
	}
}



