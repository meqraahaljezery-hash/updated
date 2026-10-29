package com.example.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val studentId: String,
    val studentName: String,
    val studentCode: String,
    val packageMinutes: Int,
    val priceEtb: Int,
    val transactionRef: String,
    val paymentMethod: String,
    val timestamp: Long,
    val status: String
)
