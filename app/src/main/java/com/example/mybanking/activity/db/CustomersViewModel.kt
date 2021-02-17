package com.example.mybanking.activity.db

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomersViewModel(application: Application):AndroidViewModel(application) {

    val allCustomers:LiveData<List<CustomersEntity>>
    private  var  repoCustomer:CustomerRepository
    private  var repoTransaction:TransactionRepository

    init {
        val daoCust = CustomersDatabase.getDatabase(application).customerDao()
        val daoTrans = CustomersDatabase.getDatabase(application).transactionDao()
        repoCustomer = CustomerRepository(daoCust)
        repoTransaction = TransactionRepository(daoTrans)
        allCustomers = repoCustomer.allCustomers

    }


    fun getBalance(id: Long):LiveData<Long>{
        return repoCustomer.getBalance(id)
    }


    fun getCustomerById(id:Long):LiveData<CustomersEntity>{
        return repoCustomer.getCustomerById(id)
    }

    fun getCustomersForTransaction(id: Long):LiveData<List<CustomersEntity>>{
        return repoCustomer.getCustomersForTransaction(id)
    }

    fun getTransactions(senderId:Long,receiverId:Long): LiveData<List<TransactionEntity>>{
        return repoTransaction.getTransactions(senderId,receiverId)
    }


    fun insertTransaction(transactionEntity: TransactionEntity) = viewModelScope.launch(Dispatchers.IO) {
        repoTransaction.insertTransaction(transactionEntity)
    }

    fun updateBalance(updatedBalance:Long,id:Long) = viewModelScope.launch(Dispatchers.IO) {
        repoCustomer.updateBalance(updatedBalance,id)
    }




}