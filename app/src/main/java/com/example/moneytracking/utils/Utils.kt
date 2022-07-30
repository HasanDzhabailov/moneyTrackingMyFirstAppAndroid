package com.example.moneytracking.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat

fun convertLongToDateString(systemTime: Long): String {
	return SimpleDateFormat("MM-dd-yyyy HH:mm")
		.format(systemTime).toString()
}
fun getColorPieChart(): MutableList<Int> {
	val colorList: MutableList<Int> = mutableListOf()
	colorList.add(0, Color.parseColor("#cc66cc"))
	colorList.add(1, Color.parseColor("#ff751f"))
	colorList.add(2, Color.parseColor("#808000"))
	colorList.add(3, Color.parseColor("#800080"))
	colorList.add(4, Color.parseColor("#ffdab9"))
	colorList.add(5, Color.parseColor("#483d8b"))
	colorList.add(6, Color.parseColor("#d8bfd8"))
	colorList.add(7, Color.parseColor("#ff1493"))
	return colorList
}

fun View.hideKeyboard() {
	val inputManager =
		context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputManager.hideSoftInputFromWindow(windowToken, 0)
}











