package com.example.moneytracking.di

import com.example.moneytracking.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {
	@ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
	abstract fun contributeMainActivity(): MainActivity
}