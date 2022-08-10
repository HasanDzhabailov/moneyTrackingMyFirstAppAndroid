package com.example.moneytracking.ui.updateexpenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import com.example.moneytracking.ui.home.HomeViewModel

class UpdateExpensesViewModelFactory (private val dataSource: MoneyTrackDatabaseDao) :
	ViewModelProvider.Factory {
	@Suppress("unchecked_cast")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(UpdateExpensesViewModel::class.java)) {
			return UpdateExpensesViewModel(dataSource) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}