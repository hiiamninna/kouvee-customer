package com.example.kouveecustomer.presenter

import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.repository.ProductRepositoryCallback

interface ProductView : ProductRepositoryCallback<ProductResponse> {
    fun showProductLoading()
    fun hideProductLoading()
}