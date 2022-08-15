package com.example.moneytracking.repository

import androidx.lifecycle.LiveData
import com.example.moneytracking.model.CategorySum
import com.example.moneytracking.model.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoneyRepository @Inject constructor(private val moneyTrackDao: MoneyTrackDatabaseDao) {
	fun getHistoryExpenses(startPeriod:Long,endPeriod:Long): LiveData<List<MoneyTrack>> {
		return moneyTrackDao.getExpensesPeriodOfTime(startPeriod,endPeriod)
	}
	fun getSumCategoryExpenses(startPeriod:Long, endPeriod:Long):LiveData<List<CategorySum>>{
		return moneyTrackDao.getSum(startPeriod, endPeriod)
	}

	fun getSumExpenses(startPeriod:Long, endPeriod:Long):LiveData<Long>{
		return moneyTrackDao.getSumPeriodOfTime(startPeriod,endPeriod)
	}

	suspend fun insert(expenses: MoneyTrack) {
		moneyTrackDao.insert(expenses)
	}

	 suspend fun delete(expenses: MoneyTrack){
		moneyTrackDao.delete(expenses)
	}

	suspend fun update(expenses: MoneyTrack){
		moneyTrackDao.update(expenses)
	}
}