package com.example.mybanking.activity.db

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData

class CustomerRepository(private val custDao:CustomersDao) {

    val allCustomers :LiveData<List<CustomersEntity>> = custDao.getAllCustomers()


    fun getCustomerById(id:Long):LiveData<CustomersEntity>{
        return custDao.getCustomerById(id)
    }

    fun getCustomersForTransaction(id: Long):LiveData<List<CustomersEntity>>{
        return custDao.getCustomersForTransaction(id)
    }

    fun getBalance(id: Long):LiveData<Long>{
        return custDao.getBalance(id)
    }

    suspend fun updateBalance(updatedBalance:Long, id:Long){
        custDao.updateBalance(updatedBalance,id)
    }

}