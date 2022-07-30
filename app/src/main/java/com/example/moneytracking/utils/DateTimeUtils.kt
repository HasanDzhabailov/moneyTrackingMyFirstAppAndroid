package com.example.moneytracking.utils

import java.text.SimpleDateFormat
import java.util.*



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
	c.set(Calendar.DAY_OF_WEEK, c.firstDayOfWeek)
	c[Calendar.HOUR_OF_DAY] = 0
	c[Calendar.MINUTE] = 0
	c[Calendar.SECOND] = 0
	c[Calendar.MILLISECOND] = 0
	return c.timeInMillis
}

fun getEndWeek(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_WEEK, 8)
	c[Calendar.HOUR_OF_DAY] = 23
	c[Calendar.MINUTE] = 59
	c[Calendar.SECOND] = 59
	c[Calendar.MILLISECOND] = 999
	return c.timeInMillis
}

fun getStartMonth(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH))
	c[Calendar.HOUR_OF_DAY] = 0
	c[Calendar.MINUTE] = 0
	c[Calendar.SECOND] = 0
	c[Calendar.MILLISECOND] = 0
	return c.timeInMillis
}

fun getEndMonth(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH))
	c[Calendar.HOUR_OF_DAY] = 23
	c[Calendar.MINUTE] = 59
	c[Calendar.SECOND] = 59
	c[Calendar.MILLISECOND] = 999
	return c.timeInMillis
}

fun getStartYear(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR))
	c[Calendar.HOUR_OF_DAY] = 0
	c[Calendar.MINUTE] = 0
	c[Calendar.SECOND] = 0
	c[Calendar.MILLISECOND] = 0
	return c.timeInMillis
}

fun getEndYear(): Long {
	val c = Calendar.getInstance()
	c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR))
	c[Calendar.HOUR_OF_DAY] = 23
	c[Calendar.MINUTE] = 59
	c[Calendar.SECOND] = 59
	c[Calendar.MILLISECOND] = 999
	return c.timeInMillis
}