package com.example.kouveecustomer.presenter

import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.repository.ProductRepositoryCallback
import com.example.kouveecustomer.repository.Repository

//PRODUCT
class ProductPresenter(private val view : ProductView, private val repository: Repository){

    fun getAllProduct(){
        view.showProductLoading()
        repository.getAllProduct(object : ProductRepositoryCallback<ProductResponse> {
            override fun productSuccess(data: ProductResponse?) {
                view.productSuccess(data)
                view.hideProductLoading()
            }
            override fun productFailed() {
                view.productFailed()
                view.hideProductLoading()
            }
        })
    }
}