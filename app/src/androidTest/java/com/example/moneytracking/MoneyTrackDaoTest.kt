package com.example.moneytracking

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moneytracking.model.MoneyTrack
import com.example.moneytracking.database.MoneyTrackDatabase
import com.example.moneytracking.database.MoneyTrackDatabaseDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertNotEquals

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MoneyTrackDaoTest{

	private lateinit var moneyDao: MoneyTrackDatabaseDao
	private lateinit var db: MoneyTrackDatabase
	@Before
	fun createDb() {
		val context: Context = ApplicationProvider.getApplicationContext()
		// Using an in-memory database because the information stored here disappears when the
		// process is killed.
		db = Room.inMemoryDatabaseBuilder(context, MoneyTrackDatabase::class.java)
			// Allowing main thread queries, just for testing.
			.allowMainThreadQueries()
			.build()
		moneyDao = db.moneyTrackDatabaseDao
	}
	@After
	@Throws(IOException::class)
	fun closeDb() {
		db.close()
	}
	@Test
	@Throws(Exception::class)
	fun insertExpenseDb() = runBlocking {
		val expense = MoneyTrack(expenseId = 123L, categoryExpense = "Другое", sumExpense = 250, dateExpense = 1660315745000 )
		moneyDao.insert(expense)

	}
	@Test
	@Throws(Exception::class)
	fun updateExpenseDb() = runBlocking {
		val expenseAdd = MoneyTrack(expenseId = 123L, categoryExpense = "Другое", sumExpense = 250, dateExpense = 1660315745000 )
		moneyDao.insert(expenseAdd)
		val expense = MoneyTrack(expenseId = 123L, categoryExpense = "Проезд", sumExpense = 400, dateExpense = 1660315745000)
		moneyDao.update(expense)

	}
	@Test
	@Throws(Exception::class)
	fun deleteExpensesDb() = runBlocking {
		val expense = MoneyTrack(expenseId = 123L, categoryExpense = "Другое", sumExpense = 250, dateExpense = 1660315745000 )
		moneyDao.insert(expense)
		moneyDao.delete(expense)
	}

}