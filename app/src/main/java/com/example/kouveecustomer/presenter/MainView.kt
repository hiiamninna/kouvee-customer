package com.example.kouveecustomer.presenter

import com.example.kouveecustomer.model.DetailServiceTransactionResponse
import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.model.TransactionResponse
import com.example.kouveecustomer.repository.DetailServiceTransactionRepositoryCallback
import com.example.kouveecustomer.repository.ProductRepositoryCallback
import com.example.kouveecustomer.repository.TransactionRepositoryCallback

interface ProductView : ProductRepositoryCallback<ProductResponse> {
    fun showProductLoading()
    fun hideProductLoading()
}

interface TransactionView: TransactionRepositoryCallback<TransactionResponse> {
    fun showTransactionLoading()
    fun hideTransactionLoading()
}

interface DetailServiceTransactionView: DetailServiceTransactionRepositoryCallback<DetailServiceTransactionResponse> {
    fun showDetailServiceTransactionLoading()
    fun hideDetailServiceTransactionLoading()
}