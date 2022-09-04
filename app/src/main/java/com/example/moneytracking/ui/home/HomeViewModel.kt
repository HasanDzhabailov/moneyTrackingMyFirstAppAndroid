package com.example.moneytracking.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.model.CategorySum
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import com.example.moneytracking.repository.MoneyRepository
import javax.inject.Inject

class HomeViewModel  @Inject constructor (dataSource: MoneyTrackDatabaseDao):ViewModel(),
	ViewModelProvider.Factory {
	private val repository: MoneyRepository = MoneyRepository(dataSource)
	fun getSumCategoryExpenses(startPeriod:Long, endPeriod:Long): LiveData<List<CategorySum>> {
		return repository.getSumCategoryExpenses(startPeriod, endPeriod)
	}
	fun getSumExpenses(startPeriod:Long, endPeriod:Long): LiveData<Long> {
		return repository.getSumExpenses(startPeriod, endPeriod)
	}
}