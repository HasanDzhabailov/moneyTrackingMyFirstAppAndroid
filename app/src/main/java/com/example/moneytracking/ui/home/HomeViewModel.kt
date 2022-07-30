package com.example.moneytracking.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moneytracking.model.CategorySum
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import com.example.moneytracking.repository.MoneyRepository

class HomeViewModel(dataSource: MoneyTrackDatabaseDao):ViewModel() {
	val database = dataSource
	private val repository: MoneyRepository = MoneyRepository(database)
	fun getSumCategoryExpenses(startPeriod:Long, endPeriod:Long): LiveData<List<CategorySum>> {
		return repository.getSumCategoryExpenses(startPeriod, endPeriod)
	}
	fun getSumExpenses(startPeriod:Long, endPeriod:Long): LiveData<Long> {
		return repository.getSumExpenses(startPeriod, endPeriod)
	}
}