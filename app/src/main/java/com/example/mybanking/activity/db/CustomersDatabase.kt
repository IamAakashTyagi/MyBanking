package com.example.mybanking.activity.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [CustomersEntity::class,TransactionEntity::class],version = 2,exportSchema = false)
abstract class CustomersDatabase: RoomDatabase() {

    abstract fun customerDao():CustomersDao
    abstract fun transactionDao():TransactionDao

    companion object{
        @Volatile
        private var instance:CustomersDatabase? = null

        fun getDatabase(context: Context) = instance?: synchronized(this){
            Room.databaseBuilder(context.applicationContext,CustomersDatabase::class.java,"customer_db").addCallback(object :RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        instance?.customerDao()?.insertCustomer(DataGenerator.getUsers())
                    }
                }
            }). build().also { instance = it }

        }
    }

    //Dummy Data Generation at the Time of Creation of DataBase ...
    class DataGenerator{
        companion object{
            fun getUsers():List<CustomersEntity>{
                return listOf(
                    CustomersEntity(0,"Akash","akash@gmail.com",1000),
                    CustomersEntity(0,"Rishab","rishab@gmail.com",1000),
                    CustomersEntity(0,"Abhishek","abhi@gmail.com",1000),
                    CustomersEntity(0,"Ayush","ayush@gmail.com",1000),
                    CustomersEntity(0,"Sadaqt","sdqt@gmail.com",1000),
                    CustomersEntity(0,"Tushar","tushar@gmail.com",1000),
                    CustomersEntity(0,"Naman","naman@gmail.com",1000),
                    CustomersEntity(0,"Ashutosh","ashu@gmail.com",1000),
                    CustomersEntity(0,"Ritik","ritik@gmail.com",1000),
                    CustomersEntity(0,"Bhupendra","bhupo@gmail.com",1000)
                )
            }
        }
    }


}
