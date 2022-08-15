package com.example.moneytracking.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneytracking.ui.addexpenses.AddExpensesViewModel
import com.example.moneytracking.ui.costhistory.CostHistoryViewModel
import com.example.moneytracking.ui.home.HomeViewModel
import com.example.moneytracking.ui.updateexpenses.UpdateExpensesViewModel
import com.example.moneytracking.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

	@Binds
	@IntoMap
	@ViewModelKey(HomeViewModel::class)
	abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(AddExpensesViewModel::class)
	abstract fun bindAddExpensesViewModel(addExpensesViewModel: AddExpensesViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(CostHistoryViewModel::class)
	abstract fun bindCostHistoryViewModel(costHistoryViewModel: CostHistoryViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(UpdateExpensesViewModel::class)
	abstract fun bindUpdateExpensesViewModel(updateExpensesViewModel: UpdateExpensesViewModel): ViewModel

	@Binds
	abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}