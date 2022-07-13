package com.example.moneytracking.repository

import androidx.lifecycle.LiveData
import com.example.moneytracking.*
import com.example.moneytracking.database.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import java.time.Month

class MoneyRepository(private val moneyTrackDao: MoneyTrackDatabaseDao) {
	val allExpenses: LiveData<List<MoneyTrack>> = moneyTrackDao.getExpenses()

	 fun getSumExpenses(): LiveData<Long> {
		return  moneyTrackDao.getSum()
	}
	fun getSumExpensesToDay():LiveData<Long>{
		return moneyTrackDao.getSumPeriodOfTime(getStartDay(), getEndDay())
	}
	fun getSumExpensesToWeek():LiveData<Long>{
		return moneyTrackDao.getSumPeriodOfTime(getStartWeek(), getEndWeek())
	}
	fun getSumExpensesToMonth():LiveData<Long>{
		return moneyTrackDao.getSumPeriodOfTime(getStartMonth(), getEndMonth())
	}
	fun getSumExpensesToYear():LiveData<Long>{
		return moneyTrackDao.getSumPeriodOfTime(getStartYear(), getEndYear())
	}
	suspend fun insert(expenses: MoneyTrack) {
		moneyTrackDao.insert(expenses)
	}

}