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
import com.example.kouveemanagement.model.PetSize
import kotlinx.android.extensions.LayoutContainer

class DetailTransactionRecyclerViewAdapter(private val detailServices: MutableList<DetailServiceTransaction>, private val listenerS: (DetailServiceTransaction) -> Unit,
                                           private val services: MutableList<Service>, private val petSizes: MutableList<PetSize>)
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
        holder.bindService(detailServices[position], listenerS, services, petSizes)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var id: TextView = itemView.findViewById(R.id.id_transaction)
        private var quantity: TextView = itemView.findViewById(R.id.quantity)
        private var subtotal: TextView = itemView.findViewById(R.id.subtotal)

        fun bindService(detailServiceTransaction: DetailServiceTransaction, listener: (DetailServiceTransaction) -> Unit,
                        services: MutableList<Service>, sizes: MutableList<PetSize>){
            var price = ""
            var name  = ""
            var sizeTxt = ""
            for (service in services){
                if (detailServiceTransaction.id_service.equals(service.id)){
                    name = service.name.toString()
                    price = service.price.toString()
                    sizeTxt = service.id_size.toString()
                }
            }
            for (size in sizes){
                if (size.id.equals(sizeTxt)){
                    sizeTxt = size.name.toString()
                }
            }
            id.text = name + " $sizeTxt"
            quantity.text = detailServiceTransaction.quantity.toString() + " x " + CustomFun.changeToRp(price.toDouble())
            val total = detailServiceTransaction.subtotal_price.toString()
            subtotal.text = CustomFun.changeToRp(total.toDouble())
            containerView.setOnClickListener {
                listener(detailServiceTransaction)
            }
        }

    }


}