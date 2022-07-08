package com.example.moneytracking.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.database.MoneyTrackDatabaseDao


class CostHistoryViewModelFactory(
	private val dataSource: MoneyTrackDatabaseDao
) : ViewModelProvider.Factory {
	@Suppress("unchecked_cast")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(CostHistoryViewModel::class.java)) {
			return CostHistoryViewModel(dataSource) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}