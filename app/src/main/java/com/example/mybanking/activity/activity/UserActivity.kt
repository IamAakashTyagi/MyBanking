package com.example.mybanking.activity.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybanking.R
import com.example.mybanking.activity.adapter.SearchCustomerAdapter
import com.example.mybanking.activity.db.CustomersEntity
import com.example.mybanking.activity.db.CustomersViewModel

class UserActivity : AppCompatActivity() {

    private lateinit var toolbar:Toolbar
    private lateinit var linearLayoutSearch:LinearLayout
    private lateinit var transferBtn:Button
    private lateinit var searchView: SearchView
    private lateinit var viewModel:CustomersViewModel
    private lateinit var txtEmail:TextView
    private lateinit var txtBalance:TextView
    private lateinit var recycleViewSearch:RecyclerView
    private  var recycleAdapter:SearchCustomerAdapter? = null
    private lateinit var layoutManager:RecyclerView.LayoutManager
    private lateinit var rootView:RelativeLayout
    private lateinit var allCustomers:ArrayList<CustomersEntity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)


        val customerId:Long?


        //Initializing viewModel...
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CustomersViewModel::class.java)


        /*Getting Customer Id from Intent and getting his/her Data from ViewModel
        and calling function @setUpFields to show related data to the screen...
       */

        customerId = if(intent != null){
            intent.getLongExtra("id",0)
        } else{
            -1
        }

        viewModel.getCustomerById(customerId).observe(this, {
            setUpFields(it.name,it.email,it.balance)
        })

        //Setting Toolbar..
        toolbar = findViewById(R.id.toolbarUser)
        setUPToolBar()


        linearLayoutSearch = findViewById(R.id.llSearchLayout)
        linearLayoutSearch.visibility = View.GONE
        transferBtn = findViewById(R.id.btnTransfer)
        transferBtn.visibility = View.VISIBLE
        searchView = findViewById(R.id.searchViewUser)
        txtEmail = findViewById(R.id.txtEmailUser)
        txtBalance = findViewById(R.id.txtUserCurrentBalance)
        recycleViewSearch = findViewById(R.id.recycleViewSearchUser)
        rootView = findViewById(R.id.rlRootUser)
        layoutManager = LinearLayoutManager(this)



        //Gettingg Data for Customers Which we have to send Money except sender Customer
        viewModel.getCustomersForTransaction(customerId).observe(this, {
            it?.let {
//                recycleAdapter.updateCustomersList(it as ArrayList<CustomersEntity>,customerId)
                  allCustomers = it as ArrayList<CustomersEntity>
            }
        })



        //Setting Click Listner on Tranfer Btn..
        transferBtn.setOnClickListener {
            recycleAdapter = SearchCustomerAdapter(this,allCustomers,customerId)
            //Setting up adapter and layout manager to Search Recycle View..
            recycleViewSearch.adapter = recycleAdapter
            recycleViewSearch.layoutManager = layoutManager
            linearLayoutSearch.visibility = View.VISIBLE
            transferBtn.visibility = View.GONE

        }


       searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(query: String?): Boolean {
               return false
           }

           override fun onQueryTextChange(newText: String?): Boolean {
               recycleAdapter?.filter?.filter(newText)
               return true
           }

       })

    }

    //Function for setting ToolBar..
    private fun setUPToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "User Name"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    //Addding Listner on Back Button on Toolbar..
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //Function for Setting Fields i.e title,email and Current Amount..
    private fun setUpFields(name: String,email:String,balance:Long){

        supportActionBar?.title = name
        txtEmail.text = email
        txtBalance.text = "$ $balance"

    }

    override fun onResume() {
        super.onResume()
        searchView.setQuery("", false)
        searchView.clearFocus()
        rootView.requestFocus()

    }
}