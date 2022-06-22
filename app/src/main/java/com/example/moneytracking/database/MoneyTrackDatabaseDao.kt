package com.example.moneytracking.database

import androidx.lifecycle.LiveData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoneyTrackDatabaseDao {
    @Insert
    suspend fun insert(expensive:MoneyTrack)
    @Update
    suspend fun update(expensive:MoneyTrack)

    @Query("SELECT SUM(sum_expensive) FROM money_tracker_expensive_table")
    suspend fun getAllSumExpensive():LiveData<List<MoneyTrack>>

}
