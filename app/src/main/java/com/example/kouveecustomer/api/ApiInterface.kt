package com.example.kouveecustomer.api

import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.model.TransactionResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    //PRODUCT
    @GET("product")
    fun getAllProduct(): Call<ProductResponse>

    //SERVICE
    @GET("transaction/service/{id}")
    fun getServiceTransaction(): Call<TransactionResponse>
}