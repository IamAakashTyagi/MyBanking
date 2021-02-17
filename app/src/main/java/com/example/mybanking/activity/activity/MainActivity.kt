package com.example.mybanking.activity.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mybanking.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnCustomers:Button
    private lateinit var toolbar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnCustomers = findViewById(R.id.btnCustomers)

        //Setting Up Click Listner on btnCustomers to open up Accounts Activity...
        btnCustomers.setOnClickListener {
            val intent = Intent(this@MainActivity, CustomersActivity::class.java)
            startActivity(intent)
        }

        toolbar = findViewById(R.id.toolbarMain)
        //set Action Bar...
        setUpToolBar()

    }

    //Function for Setting Tollbar..
    private fun setUpToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "My Banking"
    }



}