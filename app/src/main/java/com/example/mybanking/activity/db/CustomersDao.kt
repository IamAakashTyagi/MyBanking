package com.example.mybanking.activity.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CustomersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCustomer(customersEntity: List<CustomersEntity>)

    @Query("SELECT * FROM customers")
    fun getAllCustomers():LiveData<List<CustomersEntity>>

    @Query("SELECT * FROM customers WHERE id =:id")
    fun getCustomerById(id:Long):LiveData<CustomersEntity>

    @Query("SELECT * FROM customers WHERE id != :id")
    fun getCustomersForTransaction(id: Long):LiveData<List<CustomersEntity>>

    @Query("SELECT balance FROM customers WHERE id =:id")
    fun getBalance(id: Long):LiveData<Long>

    @Query("UPDATE customers SET balance =:updatedBalance WHERE id =:id")
    suspend fun updateBalance(updatedBalance:Long,id:Long)

}