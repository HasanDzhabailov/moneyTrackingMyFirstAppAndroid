package com.example.moneytracking.viewmodels.moneytrack

import android.app.Application
import androidx.lifecycle.*
import com.example.moneytracking.database.Category_sum
import com.example.moneytracking.database.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabaseDao

import com.example.moneytracking.repository.MoneyRepository
import kotlinx.coroutines.launch

class MoneyTrackerViewModel(
	dataSource: MoneyTrackDatabaseDao,
	application: Application,
) :
	AndroidViewModel(application) {
	val database = dataSource
	private val repository: MoneyRepository = MoneyRepository(database)

	fun addExpenses(categoryExpense: String, sumExpense: Long) {
		viewModelScope.launch {
			val newExpense = MoneyTrack(categoryExpense = categoryExpense, sumExpense = sumExpense)
			repository.insert(newExpense)
		}
	}
	fun getSumCategoryExpenses(startPeriod:Long, endPeriod:Long): LiveData<List<Category_sum>> {
		return repository.getSumCategoryExpenses(startPeriod, endPeriod)
	}
	fun getSumExpenses(startPeriod:Long, endPeriod:Long): LiveData<Long> {
		return repository.getSumExpenses(startPeriod, endPeriod)
	}

}