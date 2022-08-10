package com.example.moneytracking.ui.addexpenses

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.database.MoneyTrackDatabaseDao

class AddExpensesViewModelFactory(
    private val dataSource: MoneyTrackDatabaseDao,
    private val application: Application,
) : ViewModelProvider.Factory {
	@Suppress("unchecked_cast")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(AddExpensesViewModel::class.java)) {
			return AddExpensesViewModel(dataSource, application) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}