package com.example.moneytracking.ui.moneytrack

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.database.MoneyTrackDatabaseDao

class MoneyTrackerViewModelFactory(
    private val dataSource: MoneyTrackDatabaseDao,
    private val application: Application,
) : ViewModelProvider.Factory {
	@Suppress("unchecked_cast")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(MoneyTrackerViewModel::class.java)) {
			return MoneyTrackerViewModel(dataSource, application) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}