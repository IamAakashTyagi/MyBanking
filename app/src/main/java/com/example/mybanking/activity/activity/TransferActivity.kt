package com.example.mybanking.activity.activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybanking.R
import com.example.mybanking.activity.adapter.TransferRecycleAdapter
import com.example.mybanking.activity.db.CustomersViewModel
import com.example.mybanking.activity.db.TransactionEntity
import java.util.*
import kotlin.collections.ArrayList

class TransferActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager:RecyclerView.LayoutManager
    private lateinit var recycleAdapter:TransferRecycleAdapter
    private lateinit var editField:EditText
    private lateinit var viewModel: CustomersViewModel
    private lateinit var btnPay:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        val senderId: Long?
        val receiverId:Long?
        var senderCurrentBalance:Long? = null
        var receiverCurrentBalance:Long? = null


        //Getting sender and receiver Id's From Intent...

        if (intent != null){
            senderId = intent.getLongExtra("senderId",-1)
            receiverId = intent.getLongExtra("receiverId",-1)
        }
        else{
            senderId = -1
            receiverId = -1
        }

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(CustomersViewModel::class.java)

        //Getting receiver Data From Customers Entity and showing details at ActionBAr..
        viewModel.getCustomerById(receiverId).observe(this, {
              supportActionBar?.title = it.name
              supportActionBar?.subtitle = it.email
        })

        //Getting Current Balance of Sender...
        viewModel.getBalance(senderId).observe(this, {
            senderCurrentBalance = it
        })

        //Getting Current Balance of Receiver..
        viewModel.getBalance(receiverId).observe(this, {
            receiverCurrentBalance = it
        })


        toolbar = findViewById(R.id.toolbarTransfer)
        editField = findViewById(R.id.editTransferMoney)
        btnPay = findViewById(R.id.btnPay)
        recyclerView = findViewById(R.id.recycleViewTransfer)
        layoutManager = LinearLayoutManager(this)

        setUpToolBar()


        //Setting Up Adapter and layout to Recycle view
        recycleAdapter = TransferRecycleAdapter(this,senderId,receiverId)
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = layoutManager


        //Getting All Transaction b/w These Two Customers... and Pass it to the adapter to display...
        viewModel.getTransactions(senderId,receiverId).observe(this,{
                recycleAdapter.setTransferDataToAdapter(it as ArrayList<TransactionEntity>)
                scrolDown(it.size)
        })
        recyclerView.scrollToPosition(500)


        //Setting On Click Listner on Btn Pay...
        btnPay.setOnClickListener {
            closeKeyBoard()
            val fundTransfer: Long
            val senderAvailableBalance: Long
            val receiverUpdatedBalance: Long
            if (editField.text.toString() == "") {
                Toast.makeText(this, "Enter Amount To Be Paid", Toast.LENGTH_SHORT).show()
            } else {
                fundTransfer = editField.text.toString().toLong()
                senderAvailableBalance = senderCurrentBalance?.minus(fundTransfer) ?: 0
                receiverUpdatedBalance = receiverCurrentBalance?.plus(fundTransfer) ?: 0


                if (senderAvailableBalance < 0) {
                    Toast.makeText(this, "Insufficient Funds!!!!", Toast.LENGTH_LONG).show()
                } else {
                    //Here we Handling the Transaction

                    //Getting Current Date...
                    val date = Date()
                    val month: String = android.text.format.DateFormat.format("MMM", date) as String
                    val day: String = android.text.format.DateFormat.format("dd", date) as String

                    //Update Balance for Sender..
                    viewModel.updateBalance(senderAvailableBalance, senderId)
                    //Update Balance for Receiver..
                    viewModel.updateBalance(receiverUpdatedBalance, receiverId)
                    //Update Transactions to the Database...
                    viewModel.insertTransaction(
                        TransactionEntity(0, senderId, receiverId, fundTransfer, day + " " + month)
                    )
                    showDialog(fundTransfer)
                    recycleAdapter.notifyDataSetChanged()
                    if (recycleAdapter.itemCount > 0) {
                        scrolDown(recycleAdapter.itemCount)
                    }

                }
            }

            editField.setText("")

        }

    }

    //Function to scroll at the  Bottom of recycle view..
    private fun scrolDown(size: Int) {
            recyclerView.scrollToPosition(size -1 )
    }


    override fun onResume() {

        super.onResume()
        editField.clearFocus()
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "UserName"
        supportActionBar?.subtitle = "useremail@email.com"
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

    private fun closeKeyBoard(){
        val view = this.currentFocus
        if (view != null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }

    //Function for Showing a Custom Dialog for payment Successfully...
    fun showDialog(fund:Long){
        val dialogView = layoutInflater.inflate(R.layout.successfull,null)
        val customDialog = android.app.AlertDialog.Builder(this).setView(dialogView).show().also {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val amountPaid = dialogView.findViewById<TextView>(R.id.txtAmountDialog)
        amountPaid.text = "$" + fund.toString()
        val btnDismiss = dialogView.findViewById<Button>(R.id.btnDialog)
        btnDismiss.setOnClickListener {
            customDialog.dismiss()
        }
    }



}