package com.example.moneytracking.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class CategorySum(
	@ColumnInfo(name = "category_expense")
	val categoryExpense:String = "Другое",
	@ColumnInfo(name = "sum")
	val sumExpense: Long = 0L
)
