package com.example.mybanking.activity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    @ColumnInfo(name = "sender_id") val senderId:Long,
    @ColumnInfo(name = "receiver_id") val receiverId:Long,
    @ColumnInfo(name = "amount") val amount:Long,
    @ColumnInfo(name = "date_trans") val dateTransaction:String
)