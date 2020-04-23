package com.example.kouveecustomer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kouveecustomer.CustomFun
import com.example.kouveecustomer.R
import com.example.kouveecustomer.model.Service
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer

class ServiceRecyclerViewAdapter(private val images: MutableList<Int>, private val services: MutableList<Service>): RecyclerView.Adapter<ServiceRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(images[position], services[position])
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        private var imageV = itemView.findViewById<ImageView>(R.id.imageView)
        private var name = itemView.findViewById<TextView>(R.id.name)
        private var price = itemView.findViewById<TextView>(R.id.price)

        fun bindItem(image: Int, service: Service){
            Picasso.get().load(image).into(imageV)
            name.text = service.name
            val priceP = service.price.toString()
            price.text = CustomFun.changeToRp(priceP.toDouble())
        }
    }


}