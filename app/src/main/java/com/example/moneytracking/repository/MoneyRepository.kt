package com.example.moneytracking.repository

import androidx.lifecycle.LiveData
import com.example.moneytracking.*
import com.example.moneytracking.database.Category_sum
import com.example.moneytracking.database.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import java.time.Month

class MoneyRepository(private val moneyTrackDao: MoneyTrackDatabaseDao) {
	val allExpenses: LiveData<List<MoneyTrack>> = moneyTrackDao.getExpenses()
	fun getSumCategoryExpenses(startPeriod:Long, endPeriod:Long):LiveData<List<Category_sum>>{
		return moneyTrackDao.getSum(startPeriod, endPeriod)
	}

//	 fun getSumExpenses(): LiveData<Long> {
//		return  moneyTrackDao.getSum()
//	}
	fun getSumExpenses(startPeriod:Long, endPeriod:Long):LiveData<Long>{
		return moneyTrackDao.getSumPeriodOfTime(startPeriod,endPeriod)
	}

	suspend fun insert(expenses: MoneyTrack) {
		moneyTrackDao.insert(expenses)
	}

}