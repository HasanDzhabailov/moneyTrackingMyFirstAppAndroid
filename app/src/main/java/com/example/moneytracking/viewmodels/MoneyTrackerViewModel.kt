package com.example.moneytracking.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.moneytracking.database.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import com.example.moneytracking.formatMoney
import kotlinx.coroutines.launch

class MoneyTrackerViewModel(dataSource: MoneyTrackDatabaseDao, application: Application): AndroidViewModel(application) {

 val database = dataSource
 private val expenses = database.getExpenses()
 val expensesString = Transformations.map(expenses){expenses -> formatMoney(expenses, application.resources)}

 private suspend fun insert(money: MoneyTrack) {
   database.insert(money)
 }

 fun addExpenses(categoryExpense:String, sumExpense:Long){
  viewModelScope.launch {
   val newExpense = MoneyTrack(categoryExpense = categoryExpense, sumExpense = sumExpense )
   insert(newExpense)
  }
 }

}