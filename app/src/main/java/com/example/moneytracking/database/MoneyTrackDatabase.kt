package com.example.moneytracking.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoneyTrack::class], version = 1, exportSchema = false)
abstract class MoneyTrackDatabase : RoomDatabase() {
    abstract val moneyTrackDatabaseDao : MoneyTrackDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: MoneyTrackDatabase? = null
        fun getInstance(context: Context): MoneyTrackDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoneyTrackDatabase::class.java,
                        "money_tracker_expensive_table"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}