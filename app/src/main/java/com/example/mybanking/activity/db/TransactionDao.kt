package com.example.mybanking.activity.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM `transaction` WHERE sender_id =:senderId AND receiver_id =:receiverId OR sender_id =:receiverId and receiver_id =:senderId")
    fun getTransactions(senderId:Long,receiverId:Long):LiveData<List<TransactionEntity>>

}