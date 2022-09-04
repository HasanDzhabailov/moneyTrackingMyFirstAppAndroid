package com.example.moneytracking.ui.addexpenses


import androidx.lifecycle.*
import com.example.moneytracking.model.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabaseDao

import com.example.moneytracking.repository.MoneyRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddExpensesViewModel @Inject constructor(
	dataSource: MoneyTrackDatabaseDao,
) : ViewModel() {
	private val repository: MoneyRepository = MoneyRepository(dataSource)

	fun addExpenses(categoryExpense: String, sumExpense: Long) {
		viewModelScope.launch {
			val newExpense = MoneyTrack(categoryExpense = categoryExpense, sumExpense = sumExpense)
			repository.insert(newExpense)
		}
	}
}