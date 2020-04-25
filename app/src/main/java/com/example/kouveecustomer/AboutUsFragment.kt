package com.example.kouveecustomer

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
        val context = requireContext()
        fab_ig.setOnClickListener {
            showDialog(context, "IG")
        }
        fab_fb.setOnClickListener {
            showDialog(context, "FB")
        }
        fab_wa.setOnClickListener {
            showDialog(context, "WA")
        }
        fab_line.setOnClickListener {
            showDialog(context, "LN")
        }
        fab_web.setOnClickListener {
            showDialog(context, "WEB")
        }
        fab_loc.setOnClickListener {
            showDialog(context, "LOC")
        }
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

        first.setSpan(BackgroundColorSpan(greenNeon), 0, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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

    private fun showDialog(context: Context, type: String){
        val builder = AlertDialog.Builder(context)
        val alert: AlertDialog

        when(type){
            "IG" -> {
                builder.setTitle("@kouveepetshop")
                    .setIcon(R.drawable.instagram)
                    .setPositiveButton("Open Instagram"){ _: DialogInterface, _: Int ->
                        startNewApp("IG")
                    }
            }
            "FB" -> {
                builder.setTitle("Kouvee Pet Shop")
                    .setIcon(R.drawable.facebook)
                    .setPositiveButton("Open Facebook"){ _: DialogInterface, _: Int ->
                        startNewApp("FB")
                    }
            }
            "WA" -> {
                builder.setTitle("+62 812 3456 7890")
                    .setIcon(R.drawable.whatsapp)
                    .setPositiveButton("Open WhatsApp"){ _: DialogInterface, _: Int ->
                        startNewApp("WA")
                    }
            }
            "LN" -> {
                builder.setTitle("@kouveepetshop")
                    .setIcon(R.drawable.line)
                    .setPositiveButton("Open Line"){ _: DialogInterface, _: Int ->
                        startNewApp("LN")
                    }
            }
            "WEB" -> {
                builder.setTitle("https://www.sayanghewan.com")
                    .setIcon(R.drawable.fab_web)
                    .setPositiveButton("Open Chrome"){ _: DialogInterface, _: Int ->
                        startNewApp("WEB")
                    }
            }
            "LOC" -> {
                builder.setTitle("Our Location")
                    .setMessage(getString(R.string.about_address))
                    .setIcon(R.drawable.maps)
                    .setPositiveButton("Open Google Maps"){ _: DialogInterface, _: Int ->
                        startNewApp("LOC")
                    }
            }
        }

        alert = builder.create()
        builder.setNeutralButton("Cancel"){ _: DialogInterface, _: Int ->
            alert.dismiss()
        }
        alert.show()
    }


    private fun startNewApp(type: String){
        val context = requireContext()
        val newIntent: Intent
        val activities: List<ResolveInfo>
        val isSafe: Boolean

        try {
            // TRIPLE DOT URL SCHEME
            when(type){
                "IG" -> {
                    newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/nin.nanovila"))
                    activities = context.packageManager.queryIntentActivities(newIntent, 0)
                    isSafe = activities.isNotEmpty()
                    newIntent(isSafe, newIntent)
                }
                "FB" -> {
                    newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/novila.ninna"))
                    activities = context.packageManager.queryIntentActivities(newIntent, 0)
                    isSafe = activities.isNotEmpty()
                    newIntent(isSafe, newIntent)
                }
                "WA" -> {
                    val number = "+62877-5298-2303"
                    newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$number"))
                    activities = context.packageManager.queryIntentActivities(newIntent, 0)
                    isSafe = activities.isNotEmpty()
                    newIntent(isSafe, newIntent)
                }
                "LN" -> {
                    //SHOW PROFILE
                    newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://line.me/R/nv/profile"))
                    //SHOW WITH ID ->> https://line.me/R/ti/p/{LINE ID}
                    activities = context.packageManager.queryIntentActivities(newIntent, 0)
                    isSafe = activities.isNotEmpty()
                    newIntent(isSafe, newIntent)
                }
                "WEB" -> {
                    newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sayanghewan.com"))
                    activities = context.packageManager.queryIntentActivities(newIntent, 0)
                    isSafe = activities.isNotEmpty()
                    newIntent(isSafe, newIntent)
                }
                "LOC" -> {
                    val location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California")
                    newIntent = Intent(Intent.ACTION_VIEW, location)
                    activities = context.packageManager.queryIntentActivities(newIntent, 0)
                    isSafe = activities.isNotEmpty()
                    newIntent(isSafe, newIntent)
                }
            }
        }catch (e: ActivityNotFoundException){
            Log.d("INTENT ", e.message.toString())
        }
    }

    private fun newIntent(safe: Boolean, intent: Intent){
        if (safe) {
            startActivity(intent)
        }else{
            val context = requireContext()
            val newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/topic?id=editors_choice"))
            val activities = context.packageManager.queryIntentActivities(newIntent, 0)
            val isSafe = activities.isNotEmpty()
            if (isSafe){
                startActivity(newIntent)
            }
        }
    }

}
