package com.example.moneytracking.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moneytracking.model.CategorySum
import com.example.moneytracking.model.MoneyTrack

@Dao
interface MoneyTrackDatabaseDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insert(expensive: MoneyTrack)
	@Update
	suspend fun update(expensive: MoneyTrack)

	@Delete
	suspend fun delete(expensive: MoneyTrack)

	@Query("SELECT * from daily_money_expenses_table WHERE  date_expense BETWEEN :startPeriod AND :endPeriod ORDER BY date_expense DESC")
	fun getExpensesPeriodOfTime(startPeriod:Long, endPeriod:Long): LiveData<List<MoneyTrack>>
	@Query("SELECT category_expense,SUM(sum_expense) as sum from daily_money_expenses_table WHERE  date_expense BETWEEN :startPeriod AND :endPeriod  GROUP BY category_expense")
	 fun getSum(startPeriod:Long,endPeriod:Long):LiveData<List<CategorySum>>

	@Query("SELECT SUM(sum_expense) from daily_money_expenses_table WHERE  date_expense BETWEEN :startPeriod AND :endPeriod  ")
	fun getSumPeriodOfTime(startPeriod:Long,endPeriod:Long):LiveData<Long>
}
