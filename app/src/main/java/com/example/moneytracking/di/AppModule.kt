package com.example.moneytracking.di

import android.app.Application
import androidx.room.Room
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
	@Singleton
	@Provides
	fun provideDb(app: Application): MoneyTrackDatabase {
		return Room
			.databaseBuilder(app, MoneyTrackDatabase::class.java,"daily_money_expenses_table")
			.fallbackToDestructiveMigration()
			.build()
	}
	@Singleton
	@Provides
	fun provideUserDao(db: MoneyTrackDatabase): MoneyTrackDatabaseDao {
		return db.moneyTrackDatabaseDao()
	}
}