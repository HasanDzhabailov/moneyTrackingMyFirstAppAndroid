package com.example.moneytracking.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class MoneyTrack(
    @PrimaryKey val dataExpensive: Date,
    @ColumnInfo(name = "category_expensive") val categoryExpensive: String?,
    @ColumnInfo(name = "sum_expensive") val sumExpensive: String?
)
