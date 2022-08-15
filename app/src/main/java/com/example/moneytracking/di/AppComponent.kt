package com.example.moneytracking.di

import android.app.Application
import com.example.moneytracking.MoneyTrackApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import dagger.android.AndroidInjectionModule
@Singleton
@Component(
	modules = [
		AndroidInjectionModule::class,
		AppModule::class,
		MainActivityModule::class
	]
)



interface AppComponent {
	@Component.Builder
	interface  Builder{
		@BindsInstance
		fun application(application: Application): Builder

		fun build(): AppComponent
	}
	fun inject(moneyTracking: MoneyTrackApp)
}