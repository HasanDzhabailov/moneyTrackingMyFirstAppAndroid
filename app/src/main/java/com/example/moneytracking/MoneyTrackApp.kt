package com.example.moneytracking


import android.app.Application
import com.example.moneytracking.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector

import timber.log.Timber
import javax.inject.Inject

class MoneyTrackApp : Application(), HasAndroidInjector{
	@Inject
	lateinit var androidInjector: DispatchingAndroidInjector<Any>

	override fun onCreate() {
		super.onCreate()
		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
		AppInjector.init(this)
	}

	override fun androidInjector(): AndroidInjector<Any> = androidInjector


}