package com.example.kouveecustomer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kouveecustomer.R
import com.example.kouveecustomer.model.Customer
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_customer.view.*

class CustomerRecyclerViewAdapter (private val customer: MutableList<Customer>) : RecyclerView.Adapter<CustomerRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_customer, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int = customer.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(customer[position])
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var name: TextView = itemView.name_customer
        private var desc: TextView = itemView.tv_customer
        private var image: ImageView = itemView.pict_customer

        fun bindItem(customer: Customer) {
            name.text = customer.name
            desc.text = customer.desc
            Picasso.get().load(customer.image).fit().into(image)
        }

    }

}