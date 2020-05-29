package com.example.kouveecustomer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kouveecustomer.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        var images: MutableList<Int> = mutableListOf()
        var customers: MutableList<Customer> = mutableListOf()
    }

    private val navigationBottom = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.nav_information -> {
                val fragment = AboutUsFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_product -> {
                val fragment = ProductFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_service -> {
                val fragment = ServiceFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(newFragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, newFragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation_bottom.setOnNavigationItemSelectedListener(navigationBottom)
        getImage()
        val fragment = AboutUsFragment.newInstance()
        addFragment(fragment)
    }

    private fun getImage(){
        var i = 0
        val image = resources.obtainTypedArray(R.array.image_pet_shop)
        images.clear()
        while(i<5){
            images.add(image.getResourceId(i, 0))
            i++
        }
        image.recycle()

        val name = resources.getStringArray(R.array.customer_name)
        val desc = resources.getStringArray(R.array.customer_desc)
        val imageC = resources.obtainTypedArray(R.array.customer_profile)
        customers.clear()
        for (i in name.indices){
            customers.add(Customer(name[i], desc[i], imageC.getResourceId(i, 0)))
        }
        imageC.recycle()
    }
}
