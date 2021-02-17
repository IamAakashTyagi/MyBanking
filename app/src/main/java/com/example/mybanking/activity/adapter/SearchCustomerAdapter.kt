package com.example.mybanking.activity.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybanking.R
import com.example.mybanking.activity.activity.TransferActivity
import com.example.mybanking.activity.db.CustomersEntity
import java.util.*
import kotlin.collections.ArrayList

class SearchCustomerAdapter(val context: Context, var data:ArrayList<CustomersEntity>,
                            var senderCustommerId:Long):RecyclerView.Adapter<SearchCustomerAdapter.SearchViewHolder>() ,Filterable{

//    private var data = ArrayList<CustomersEntity>()
//    private var senderCustomerId:Long? = null
    private  var list:MutableList<CustomersEntity> = data


    class SearchViewHolder(view:View):RecyclerView.ViewHolder(view){
        var txtName:TextView = view.findViewById(R.id.txtNameSearch)
        var txtEmail:TextView = view.findViewById(R.id.txtEmailSearch)
        var txtImageLetter:TextView = view.findViewById(R.id.txtImageLetter)
        var llRecycleView:LinearLayout = view.findViewById(R.id.llSearchRecycle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.customer_search_single_view,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val customer = data[position]
        val receiverCustomerId = customer.id
        holder.txtName.text = customer.name
        holder.txtEmail.text = customer.email
        holder.txtImageLetter.text = customer.name[0].toString().toUpperCase(Locale.ROOT)

        holder.llRecycleView.setOnClickListener {
            val intent = Intent(context,TransferActivity::class.java)
            intent.putExtra("senderId",senderCustommerId)
            intent.putExtra("receiverId",receiverCustomerId)
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun getFilter(): Filter {
       return object : Filter() {
           override fun performFiltering(p0: CharSequence?): FilterResults {
               val charString:String = p0.toString()
               if (charString.isEmpty()){
                   data = list as ArrayList<CustomersEntity>
               }else{
                   val filteredList:MutableList<CustomersEntity> = mutableListOf()
                   for (a:CustomersEntity in list){
                       if (a.name.toLowerCase().contains(charString.toLowerCase(Locale.ROOT))) {
                           filteredList.add(a)
                       }
                   }
                   data = filteredList as ArrayList<CustomersEntity>
               }
               var filterResults:FilterResults = FilterResults()
               filterResults.values = data
               return filterResults
           }

           override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
               data = p1!!.values as ArrayList<CustomersEntity>
               notifyDataSetChanged()
           }
       }
    }

}