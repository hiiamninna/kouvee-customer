package com.example.kouveecustomer.presenter

import com.example.kouveecustomer.model.*
import com.example.kouveecustomer.repository.*

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

interface ServiceView: ServiceRepositoryCallback<ServiceResponse> {
    fun showServiceLoading()
    fun hideServiceLoading()
}

interface CustomerPetView: CustomerPetRepositoryCallback<CustomerPetResponse> {
    fun showCustomerPetLoading()
    fun hideCustomerPetLoading()
}