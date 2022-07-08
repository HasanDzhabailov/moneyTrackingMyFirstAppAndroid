package com.example.moneytracking

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.text.HtmlCompat
import com.example.moneytracking.database.MoneyTrack
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
	return SimpleDateFormat("MM-dd-yyyy HH:mm")
		.format(systemTime).toString()
}


fun formatMoney(nights: List<MoneyTrack>, resources: Resources): Spanned {
	val sb = StringBuilder()
	sb.apply {
		append(resources.getString(R.string.title))
		nights.forEach {
			append("<br>")
			append("${convertLongToDateString(it.dateExpense)}<br>")
			append("Категория: ${it.categoryExpense}<br>")
			append("Сумма расхода: ${it.sumExpense} руб.<br>")
		}
	}
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
	} else {
		return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
	}
}

fun View.hideKeyboard() {
	val inputManager =
		context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputManager.hideSoftInputFromWindow(windowToken, 0)
}
