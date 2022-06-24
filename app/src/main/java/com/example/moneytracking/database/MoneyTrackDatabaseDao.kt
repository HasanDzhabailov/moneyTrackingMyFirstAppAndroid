package com.example.moneytracking.database

import androidx.lifecycle.LiveData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoneyTrackDatabaseDao {
    @Insert
     fun insert(expensive:MoneyTrack)
    @Update
    suspend fun update(expensive:MoneyTrack)

    @Query("SELECT SUM(sum_expense) FROM daily_money_expenses_table")
   fun getAllSumExpensive():MoneyTrack

}
