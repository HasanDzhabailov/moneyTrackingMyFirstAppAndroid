package com.example.moneytracking.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.database.MoneyTrackDatabaseDao


class HomeViewModelFactory(private val dataSource: MoneyTrackDatabaseDao) :
	ViewModelProvider.Factory {
	@Suppress("unchecked_cast")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
			return HomeViewModel(dataSource) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}