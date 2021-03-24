package com.aukde.proveedor.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aukde.proveedor.Models.Orders
import com.aukde.proveedor.R


class AdapterOrders(private val dataSet: Array<String>) :
        RecyclerView.Adapter<AdapterOrders.ViewHolder>() {

    private lateinit var listOrder : List<Orders>

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtNumOrder : TextView = itemView.findViewById(R.id.lsNumOrder)
        var txtName : TextView = itemView.findViewById(R.id.lsName)
        var txtLastName : TextView = itemView.findViewById(R.id.lsLastName)
        var txtStatus : TextView = itemView.findViewById(R.id.lsStatus)
        var txtPhone : TextView = itemView.findViewById(R.id.lsPhone)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.row_orders, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val ls : Orders = listOrder[position]
        viewHolder.txtNumOrder.text = ls.numOrder
        viewHolder.txtName.text = ls.name
        viewHolder.txtLastName.text = ls.lastName
        viewHolder.txtStatus.text = ls.status
        viewHolder.txtPhone.text = ls.phone
    }

    override fun getItemCount() = dataSet.size

}

