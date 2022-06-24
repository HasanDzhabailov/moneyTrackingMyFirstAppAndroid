package com.example.moneytracking.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.moneytracking.database.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabaseDao

class MoneyTrackerViewModel(val database: MoneyTrackDatabaseDao, application: Application): AndroidViewModel(application) {


}