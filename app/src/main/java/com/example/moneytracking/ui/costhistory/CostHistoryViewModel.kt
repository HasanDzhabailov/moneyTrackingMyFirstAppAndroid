package com.example.moneytracking.ui.costhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moneytracking.model.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabaseDao

import com.example.moneytracking.repository.MoneyRepository

class CostHistoryViewModel (dataSource: MoneyTrackDatabaseDao) :
	ViewModel() {
	val database = dataSource
	private val repository: MoneyRepository = MoneyRepository(database)
	fun getHistoryExpensesPeriod(startPeriod:Long, endPeriod:Long): LiveData<List<MoneyTrack>> {
		return repository.getHistoryExpenses(startPeriod, endPeriod)
	}



}