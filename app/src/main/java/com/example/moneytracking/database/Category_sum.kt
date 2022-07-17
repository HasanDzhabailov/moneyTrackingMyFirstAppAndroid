package com.example.moneytracking.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Category_sum(
	@ColumnInfo(name = "category_expense")
	val categoryExpense:String = "Другое",
	@ColumnInfo(name = "sum")
	val sumExpense: Long = 0L
)
