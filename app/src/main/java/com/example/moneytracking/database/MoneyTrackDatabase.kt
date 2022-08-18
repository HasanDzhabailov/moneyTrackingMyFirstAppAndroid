package com.example.moneytracking.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneytracking.model.MoneyTrack

@Database(entities = [MoneyTrack::class], version = 1, exportSchema = false)

abstract class MoneyTrackDatabase : RoomDatabase() {
	abstract fun moneyTrackDatabaseDao(): MoneyTrackDatabaseDao
}