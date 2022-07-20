package com.example.moneytracking.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoneyTrackDatabaseDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insert(expensive: MoneyTrack)

	@Query("SELECT * from daily_money_expenses_table ORDER BY date_expense DESC")
	fun getExpenses(): LiveData<List<MoneyTrack>>

	@Query("SELECT category_expense,SUM(sum_expense) as sum from daily_money_expenses_table WHERE  date_expense BETWEEN :startPeriod AND :endPeriod  GROUP BY category_expense")
	 fun getSum(startPeriod:Long,endPeriod:Long):LiveData<List<CategorySum>>

	@Query("SELECT SUM(sum_expense) from daily_money_expenses_table WHERE  date_expense BETWEEN :startPeriod AND :endPeriod  ")
	fun getSumPeriodOfTime(startPeriod:Long,endPeriod:Long):LiveData<Long>
}
