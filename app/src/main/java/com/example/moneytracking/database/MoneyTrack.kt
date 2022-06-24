package com.example.moneytracking.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "daily_money_expenses_table")
data class MoneyTrack(
    @PrimaryKey(autoGenerate = true)
    var expenseId: Long = 0L,

    @ColumnInfo(name = "category_expense")
    val categoryExpense: String? = "",

    @ColumnInfo(name = "sum_expense")
    val sumExpense: Long = 0L,

    @ColumnInfo(name = "date_expense")
    val dateExpense:Long = System.currentTimeMillis()
)
