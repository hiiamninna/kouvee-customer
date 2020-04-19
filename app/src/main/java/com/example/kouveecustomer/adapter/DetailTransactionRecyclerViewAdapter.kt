package com.example.kouveecustomer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kouveecustomer.CustomFun
import com.example.kouveecustomer.R
import com.example.kouveecustomer.model.DetailServiceTransaction
import com.example.kouveecustomer.model.Service
import kotlinx.android.extensions.LayoutContainer

class DetailTransactionRecyclerViewAdapter(private val detailServices: MutableList<DetailServiceTransaction>, private val listenerS: (DetailServiceTransaction) -> Unit, private val services: MutableList<Service>)
    : RecyclerView.Adapter<DetailTransactionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_transaction, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return detailServices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindService(detailServices[position], listenerS, services)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var id: TextView = itemView.findViewById(R.id.id_transaction)
        private var quantity: TextView = itemView.findViewById(R.id.quantity)
        private var subtotal: TextView = itemView.findViewById(R.id.subtotal)

        fun bindService(detailServiceTransaction: DetailServiceTransaction, listener: (DetailServiceTransaction) -> Unit, services: MutableList<Service>){
            for (service in services){
                if (detailServiceTransaction.id_service.equals(service.id)){
                    id.text = service.name
                }
            }
            quantity.text = detailServiceTransaction.quantity.toString()
            val total = detailServiceTransaction.subtotal_price.toString()
            subtotal.text = CustomFun.changeToRp(total.toDouble())
            containerView.setOnClickListener {
                listener(detailServiceTransaction)
            }
        }

    }


}