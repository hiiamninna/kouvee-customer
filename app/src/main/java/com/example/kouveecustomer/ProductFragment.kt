package com.example.kouveecustomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kouveecustomer.adapter.ProductRecyclerViewAdapter
import com.example.kouveecustomer.model.Product
import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.presenter.ProductPresenter
import com.example.kouveecustomer.presenter.ProductView
import com.example.kouveecustomer.repository.Repository
import kotlinx.android.synthetic.main.fragment_product.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment(), ProductView {

    private lateinit var productAdapter: ProductRecyclerViewAdapter

    private lateinit var presenter: ProductPresenter
    private var productsList: MutableList<Product> = mutableListOf()

    private val productsSorted = ArrayList<Product>()
    private var selectedItem = -1

    companion object {
        fun newInstance() = ProductFragment()
        var products: MutableList<Product> = mutableListOf()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProductPresenter(this, Repository())
        presenter.getAllProduct()

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                recyclerview.adapter = ProductRecyclerViewAdapter(products){
                    Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
                }
                query?.let { productAdapter.filterProduct(it) }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerview.adapter = ProductRecyclerViewAdapter(products){
                    Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
                }
                newText?.let { productAdapter.filterProduct(it) }
                return false
            }
        })
        sort.setOnClickListener {
            showFilterDialog()
        }
        swipe_rv.setOnRefreshListener {
            presenter.getAllProduct()
        }
        CustomFun.setSwipe(swipe_rv)
    }

    private fun showFilterDialog(){
        val sort = arrayOf("Highest price", "Lowest price", "Maximum stock", "Minimum stock")
        val dialogBuilder = context?.let { AlertDialog.Builder(it) }
        dialogBuilder?.setTitle("Sort By")
        dialogBuilder?.setSingleChoiceItems(sort, selectedItem) {
                _, which ->
            selectedItem = which
        }
        dialogBuilder?.setPositiveButton("APPLY"){
                dialog, _ ->
            when (selectedItem) {
                0 -> {
                    sortByPriceAz()
                }
                1 -> {
                    sortByPriceZa()
                }
                2 -> {
                    sortByStockAz()
                }
                3 -> {
                    sortByStockZa()
                }
                else -> {
                    dialog.dismiss()
                }
            }
            selectedItem = -1
            dialog.dismiss()
        }
        dialogBuilder?.setNegativeButton("CANCEL"){
                dialog, _ ->
            selectedItem = -1
            dialog.dismiss()
        }
        dialogBuilder?.show()
    }

    private fun sortByPriceAz(){
        productsSorted.removeIf{ it.price == null}
        val sorted = productsSorted.sortedBy { it.price }
        recyclerview.adapter = ProductRecyclerViewAdapter(sorted as MutableList<Product>){
            Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
        }
        productAdapter.notifyDataSetChanged()
    }

    private fun sortByPriceZa(){
        productsSorted.removeIf{ it.price == null}
        val sorted = productsSorted.sortedByDescending { it.price }
        recyclerview.adapter = ProductRecyclerViewAdapter(sorted as MutableList<Product>){
            Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
        }
        productAdapter.notifyDataSetChanged()
    }

    private fun sortByStockAz(){
        productsSorted.removeIf{ it.stock == null}
        val sorted = productsSorted.sortedBy { it.stock }
        recyclerview.adapter = ProductRecyclerViewAdapter(sorted as MutableList<Product>){
            Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
        }
        productAdapter.notifyDataSetChanged()
    }

    private fun sortByStockZa(){
        productsSorted.removeIf{ it.stock == null}
        val sorted = productsSorted.sortedByDescending { it.stock }
        recyclerview.adapter = ProductRecyclerViewAdapter(sorted as MutableList<Product>){
            Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
        }
        productAdapter.notifyDataSetChanged()
    }

    override fun showProductLoading() {
        swipe_rv.isRefreshing = true
    }

    override fun hideProductLoading() {
        swipe_rv.isRefreshing = false
    }

    override fun productSuccess(data: ProductResponse?) {
        val temp = data?.products ?: emptyList()
        if (temp.isEmpty()){
            Toast.makeText(context, "Empty Product", Toast.LENGTH_LONG).show()
        }else{
            clearList()
            productsList.addAll(temp)
            productsSorted.addAll(productsList)
            recyclerview.apply {
                layoutManager = GridLayoutManager(context, 2)
                productAdapter = ProductRecyclerViewAdapter(productsList){
                    Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
                }
                adapter = productAdapter
            }
            Toast.makeText(context, "Success Product", Toast.LENGTH_LONG).show()
        }
    }

    override fun productFailed() {
        Toast.makeText(context, "Product Failed", Toast.LENGTH_LONG).show()
    }

    private fun clearList(){
        productsList.clear()
        productsSorted.clear()
    }

}
