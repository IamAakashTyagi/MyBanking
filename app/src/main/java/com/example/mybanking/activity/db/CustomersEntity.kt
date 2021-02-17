package com.example.mybanking.activity.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomersEntity (
        @PrimaryKey(autoGenerate = true) val id:Long,
        val name:String,
        val email:String,
        val balance:Long
    )
