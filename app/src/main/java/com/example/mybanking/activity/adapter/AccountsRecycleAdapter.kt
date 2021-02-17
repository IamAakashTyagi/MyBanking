package com.example.mybanking.activity.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybanking.R
import com.example.mybanking.activity.activity.UserActivity
import com.example.mybanking.activity.db.CustomersEntity

class AccountsRecycleAdapter(val context:Context):RecyclerView.Adapter<AccountsRecycleAdapter.AccountsViewHolder>() {

    val data = ArrayList<CustomersEntity>()

    class AccountsViewHolder(view:View):RecyclerView.ViewHolder(view){

        var txtName:TextView = view.findViewById(R.id.txtName)
        var txtEmail:TextView = view.findViewById(R.id.txtEmail)
        var txtCurrentBalance:TextView = view.findViewById(R.id.txtCurrentBalance)
        var cardLayout:CardView = view.findViewById(R.id.cardViewSingle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_accounts_single_view,parent,false)
        return AccountsViewHolder(view)
    }


    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val customer = data[position]
        holder.txtName.text = customer.name
        holder.txtEmail.text = customer.email
        holder.txtCurrentBalance.text = "$ " +customer.balance.toString()
        val id = customer.id

        holder.cardLayout.setOnClickListener {
            var intent = Intent(context, UserActivity::class.java)
            intent.putExtra("id",id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateCustomersList(newList:ArrayList<CustomersEntity>){
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }
}