package com.example.mybanking.activity.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybanking.R
import com.example.mybanking.activity.adapter.AccountsRecycleAdapter
import com.example.mybanking.activity.db.CustomersEntity
import com.example.mybanking.activity.db.CustomersViewModel

class CustomersActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleAdapter: AccountsRecycleAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewModel: CustomersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers)


        toolbar = findViewById(R.id.toolbarAccounts)

        //Setting Up ToolBar..
        setUpToolBar()

        recyclerView = findViewById(R.id.recyclerViewAccounts)



        //Setting Recycler View..
        layoutManager = LinearLayoutManager(this)
        recycleAdapter = AccountsRecycleAdapter(this)
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = layoutManager

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CustomersViewModel::class.java)
        viewModel.allCustomers.observe(this, {
            it?.let {
                recycleAdapter.updateCustomersList(it as ArrayList<CustomersEntity>)
            }

        })

    }

    //Function for SetUpToolBar..
    private fun setUpToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "CustomersEntity"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}