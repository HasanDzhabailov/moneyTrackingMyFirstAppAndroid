package com.example.moneytracking.di

import com.example.moneytracking.ui.addexpenses.AddExpensesFragment
import com.example.moneytracking.ui.costhistory.CostHistoryFragment
import com.example.moneytracking.ui.home.HomeFragment
import com.example.moneytracking.ui.updateexpenses.UpdateExpensesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
	@ContributesAndroidInjector
	abstract fun contributeHomeFragmentFragment(): HomeFragment

	@ContributesAndroidInjector
	abstract fun contributeAddExpensesFragment(): AddExpensesFragment

	@ContributesAndroidInjector
	abstract fun contributeUpdateExpensesFragment(): UpdateExpensesFragment

	@ContributesAndroidInjector
	abstract fun contributeCostHistoryFragment(): CostHistoryFragment
}