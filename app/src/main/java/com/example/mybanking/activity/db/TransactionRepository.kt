package com.example.mybanking.activity.db

import androidx.lifecycle.LiveData

class TransactionRepository(private val transDao:TransactionDao) {

    suspend fun insertTransaction(transactionEntity: TransactionEntity){
        transDao.insertTransaction(transactionEntity)
    }

    fun getTransactions(senderId:Long,receiverId:Long): LiveData<List<TransactionEntity>>{
        return transDao.getTransactions(senderId,receiverId)
    }
}