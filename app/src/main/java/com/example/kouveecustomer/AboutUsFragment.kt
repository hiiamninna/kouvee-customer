package com.example.kouveecustomer

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kouveecustomer.adapter.CustomerRecyclerViewAdapter
import com.example.kouveecustomer.adapter.ImageRecyclerViewAdapter
import com.example.kouveecustomer.adapter.ServiceRecyclerViewAdapter
import com.example.kouveecustomer.model.Service
import com.example.kouveecustomer.model.ServiceResponse
import com.example.kouveecustomer.presenter.ServicePresenter
import com.example.kouveecustomer.presenter.ServiceView
import com.example.kouveecustomer.repository.Repository
import kotlinx.android.synthetic.main.fragment_about_us.*

/**
 * A simple [Fragment] subclass.
 */
class AboutUsFragment : Fragment(), ServiceView {

    private val presenter = ServicePresenter(this, Repository())
    private var enServices: MutableList<Service> = mutableListOf()

    private var images: MutableList<Int> = mutableListOf()

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
        presenter.getAllService()
        setMotto()
        setImage()
        setCustomer()
    }

    private fun setImage(){
        rv_image.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_image.adapter = ImageRecyclerViewAdapter(MainActivity.images)
    }

    private fun setCustomer(){
        rv_customer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_customer.adapter = CustomerRecyclerViewAdapter(MainActivity.customers)
    }

    private fun setResource(){
        var i = 0
        val image = resources.obtainTypedArray(R.array.image_service)
        images.clear()
        while(i<4){
            images.add(image.getResourceId(i, 0))
            i++
        }
        image.recycle()
    }

    private fun setMotto(){
        val yellowNeon = Color.parseColor("#f9ff21")
        val orangeNeon = Color.parseColor("#dbff3d")
        val greenNeon = Color.parseColor("#fffb97")

        val first = SpannableString("\"Caring your beloved pets\"")
        val second = SpannableString("\"Treat your pets as our friend\"")
        val third = SpannableString("\"Comfortable place for pets\"")

        first.setSpan(BackgroundColorSpan(greenNeon), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        motto_1.text = first
        second.setSpan(BackgroundColorSpan(yellowNeon), 19, 31, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        motto_2.text = second
        third.setSpan(BackgroundColorSpan(orangeNeon), 0, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        motto_3.text = third
    }

    override fun showServiceLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideServiceLoading() {
        progress_circular.visibility = View.GONE
    }

    override fun serviceSuccess(data: ServiceResponse?) {
        setResource()
        val temp: List<Service> = data?.services ?: emptyList()
        if (temp.isNotEmpty()){
            enServices.clear()
            for (i in temp.indices){
                if (temp[i].deleted_at.isNullOrEmpty()){
                    enServices.add(temp[i])
                }
            }
            rv_service.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rv_service.adapter = ServiceRecyclerViewAdapter(images, enServices)
        }
    }

    override fun serviceFailed() {
    }

}
