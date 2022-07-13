package com.example.moneytracking.database


import androidx.room.*




@Entity(tableName = "daily_money_expenses_table")
data class MoneyTrack(
	@PrimaryKey(autoGenerate = true)
	var expenseId: Long = 0L,

	@ColumnInfo(name = "category_expense")
	val categoryExpense: String = "Другое",

	@ColumnInfo(name = "sum_expense")
	val sumExpense: Long = 0,

	@ColumnInfo(name = "date_expense")
	val dateExpense: Long =  System.currentTimeMillis(),
)
