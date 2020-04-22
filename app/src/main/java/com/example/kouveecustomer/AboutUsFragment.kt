package com.example.kouveecustomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kouveecustomer.adapter.CustomerRecyclerViewAdapter
import com.example.kouveecustomer.adapter.ImageRecyclerViewAdapter
import com.example.kouveecustomer.adapter.ServiceRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_about_us.*

/**
 * A simple [Fragment] subclass.
 */
class AboutUsFragment : Fragment() {

    companion object {
        fun newInstance() = AboutUsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()
        setCustomer()
        setService()
    }

    private fun setImage(){
        rv_image.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_image.adapter = ImageRecyclerViewAdapter(MainActivity.images)
    }

    private fun setCustomer(){
        rv_customer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_customer.adapter = CustomerRecyclerViewAdapter(MainActivity.customers)
    }

    private fun setService(){
        rv_service.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_service.adapter = ServiceRecyclerViewAdapter(MainActivity.enServices)
    }

}
