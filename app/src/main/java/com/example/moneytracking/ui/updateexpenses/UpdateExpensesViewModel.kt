package com.example.moneytracking.ui.updateexpenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import com.example.moneytracking.model.MoneyTrack
import com.example.moneytracking.repository.MoneyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateExpensesViewModel(dataSource: MoneyTrackDatabaseDao): ViewModel() {
	val database = dataSource
	private val repository: MoneyRepository = MoneyRepository(database)
	fun updateExpenses(expenseId:Long,categoryExpense: String, sumExpense: Long, dateExpense:Long) {
		CoroutineScope(Dispatchers.IO).launch  {
			val newExpense = MoneyTrack(expenseId = expenseId, categoryExpense = categoryExpense, sumExpense = sumExpense, dateExpense = dateExpense)
			repository.update(newExpense)
		}
	}
}