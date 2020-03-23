package com.example.kouveecustomer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kouveecustomer.adapter.ProductRecyclerViewAdapter
import com.example.kouveecustomer.model.Product
import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.presenter.ProductPresenter
import com.example.kouveecustomer.presenter.ProductView
import com.example.kouveecustomer.repository.Repository
import kotlinx.android.synthetic.main.fragment_product.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment(), ProductView {

    private lateinit var productAdapter: ProductRecyclerViewAdapter

    private lateinit var presenter: ProductPresenter
    private var products: MutableList<Product> = mutableListOf()

    private val productsSorted = ArrayList<Product>()

    companion object {
        fun newInstance() = ProductFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProductPresenter(this, Repository())
        presenter.getAllProduct()

        az_price.setOnClickListener {
            sortByPriceAz()
        }

        za_price.setOnClickListener {
            sortByPriceZa()
        }

        az_stock.setOnClickListener {
            sortByStockAz()
        }

        za_stock.setOnClickListener {
            sortByStockZa()
        }
    }

    private fun sortByPriceAz(){
        productsSorted.removeIf{ it.price == null}
        val sorted = productsSorted.sortedBy { it.price }
        productAdapter.setProducts(sorted)
        productAdapter.notifyDataSetChanged()
    }

    private fun sortByPriceZa(){
        productsSorted.removeIf{ it.price == null}
        val sorted = productsSorted.sortedByDescending { it.price }
        productAdapter.setProducts(sorted)
        productAdapter.notifyDataSetChanged()
    }

    private fun sortByStockAz(){
        productsSorted.removeIf{ it.price == null}
        val sorted = productsSorted.sortedBy { it.stock }
        productAdapter.setProducts(sorted)
        productAdapter.notifyDataSetChanged()
    }

    private fun sortByStockZa(){
        productsSorted.removeIf{ it.price == null}
        val sorted = productsSorted.sortedByDescending { it.stock }
        productAdapter.setProducts(sorted)
        productAdapter.notifyDataSetChanged()
    }

    override fun showProductLoading() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProductLoading() {
        progressbar.visibility = View.GONE
    }

    override fun productSuccess(data: ProductResponse?) {
        val temp = data?.products ?: emptyList()
        if (temp.isEmpty()){
            Toast.makeText(context, "Empty Product", Toast.LENGTH_LONG).show()
        }else{
            products.addAll(temp)
            productsSorted.addAll(products)
            recyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                productAdapter = ProductRecyclerViewAdapter{
                    Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
                }
                adapter = productAdapter
            }
            productAdapter.setProducts(products)
            Toast.makeText(context, "Success Product", Toast.LENGTH_LONG).show()
        }
    }

    override fun productFailed() {
        Toast.makeText(context, "Product Failed", Toast.LENGTH_LONG).show()
    }

}
