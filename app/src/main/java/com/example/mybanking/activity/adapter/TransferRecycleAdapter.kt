package com.example.mybanking.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybanking.R
import com.example.mybanking.activity.db.TransactionEntity

class TransferRecycleAdapter(var context:Context,var sId:Long,var rId:Long):RecyclerView.Adapter<TransferRecycleAdapter.TransferViewHolder>() {

    var data = ArrayList<TransactionEntity>()


    class TransferViewHolder(view:View):RecyclerView.ViewHolder(view){
        var amount:TextView = view.findViewById(R.id.txtTransferdMoney)
        var paidText:TextView = view.findViewById(R.id.txtPaid)
        var date:TextView = view.findViewById(R.id.txtDateTransfered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_transfer_single_view,parent,false)
        return TransferViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransferViewHolder, position: Int) {
        val rowData = data[position]
        var paidBy:String = "You were Paid"
        if (rowData.senderId == sId){
            paidBy = "You Paid"
        }

        holder.amount.text = rowData.amount.toString()
        holder.paidText.text = paidBy
        holder.date.text = rowData.dateTransaction


    }

    override fun getItemCount(): Int {
       return  data.size
    }

    fun setTransferDataToAdapter(newList:ArrayList<TransactionEntity>){
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }
}