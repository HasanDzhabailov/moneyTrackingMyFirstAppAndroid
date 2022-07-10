package com.example.moneytracking.viewmodels.moneytrack

import android.app.Application
import androidx.lifecycle.*
import com.example.moneytracking.convertLongToDateString
import com.example.moneytracking.database.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import com.example.moneytracking.formatMoney
import com.example.moneytracking.repository.MoneyRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MoneyTrackerViewModel(dataSource: MoneyTrackDatabaseDao, application: Application) :
	AndroidViewModel(application) {
	val database = dataSource
	private val repository: MoneyRepository = MoneyRepository(database)
	var sumALl = 0L
	val sumExpense: LiveData<Long> = repository.getSumExpenses()
	fun setSum(sum:Long){
		this.sumALl = sum
	}

	fun addExpenses(categoryExpense: String, sumExpense: Long) {
		viewModelScope.launch {
			val newExpense = MoneyTrack(categoryExpense = categoryExpense, sumExpense = sumExpense)
			repository.insert(newExpense)
		}
	}


}