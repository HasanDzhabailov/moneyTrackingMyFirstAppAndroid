package com.example.moneytracking.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.moneytracking.database.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import com.example.moneytracking.formatMoney
import com.example.moneytracking.repository.MoneyRepository
import kotlinx.coroutines.launch

class MoneyTrackerViewModel(dataSource: MoneyTrackDatabaseDao, application: Application) :
	AndroidViewModel(application) {
	val database = dataSource
	private val repository: MoneyRepository = MoneyRepository(database)

	private val allExpenses: LiveData<List<MoneyTrack>> = repository.allExpenses


	val expensesString = Transformations.map(allExpenses) { expenses ->
		formatMoney(expenses,
			application.resources)
	}


	fun addExpenses(categoryExpense: String, sumExpense: Long) {
		viewModelScope.launch {
			val newExpense = MoneyTrack(categoryExpense = categoryExpense, sumExpense = sumExpense)
			repository.insert(newExpense)
		}
	}

}