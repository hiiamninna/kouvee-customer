package com.example.kouveecustomer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kouveecustomer.model.CustomerPet
import com.example.kouveecustomer.model.CustomerPetResponse
import com.example.kouveecustomer.model.Service
import com.example.kouveecustomer.model.ServiceResponse
import com.example.kouveecustomer.presenter.CustomerPetPresenter
import com.example.kouveecustomer.presenter.CustomerPetView
import com.example.kouveecustomer.presenter.ServicePresenter
import com.example.kouveecustomer.presenter.ServiceView
import com.example.kouveecustomer.repository.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ServiceView, CustomerPetView {

    private var presenterS: ServicePresenter = ServicePresenter(this, Repository())
    private var presenterP: CustomerPetPresenter = CustomerPetPresenter(this, Repository())

    companion object{
        var services: MutableList<Service> = mutableListOf()
        var pets: MutableList<CustomerPet> = mutableListOf()
        var images: MutableList<Int> = mutableListOf()
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

    private fun addFragment(input: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, input, input.javaClass.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation_bottom.setOnNavigationItemSelectedListener(navigationBottom)
        getImage()
        presenterP.getAllCustomerPet()
        presenterS.getAllService()
        val fragment = AboutUsFragment.newInstance()
        addFragment(fragment)
    }

    private fun getImage(){
        var i = 0
        val image = resources.obtainTypedArray(R.array.image_pet_shop)
        images.clear()
        while(i<3){
            images.add(image.getResourceId(i, 0))
            i++
        }
        image.recycle()
    }

    override fun showServiceLoading() {
    }

    override fun hideServiceLoading() {
    }

    override fun serviceSuccess(data: ServiceResponse?) {
        val temp: List<Service> = data?.services ?: emptyList()
        if (temp.isEmpty()){
            CustomFun.warningSnackBar(container, baseContext, "Service empty")
        }else{
            services.clear()
            services.addAll(temp)
            CustomFun.successSnackBar(container, baseContext, "Service success")
        }
    }

    override fun serviceFailed() {
        CustomFun.failedSnackBar(container, baseContext, "Service failed")
    }

    override fun showCustomerPetLoading() {
    }

    override fun hideCustomerPetLoading() {
    }

    override fun customerPetSuccess(data: CustomerPetResponse?) {
        val temp: List<CustomerPet> = data?.customerpets ?: emptyList()
        if (temp.isEmpty()){
            CustomFun.warningSnackBar(container, baseContext, "Pet empty")
        }else{
            pets.clear()
            pets.addAll(temp)
            CustomFun.successSnackBar(container, baseContext, "Pet success")
        }
    }

    override fun customerPetFailed() {
        CustomFun.failedSnackBar(container, baseContext, "Pet failed")
    }
}
