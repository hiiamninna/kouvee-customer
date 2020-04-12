package com.example.kouveecustomer.api

import com.example.kouveecustomer.model.DetailServiceTransactionResponse
import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.model.TransactionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    //PRODUCT
    @GET("product")
    fun getAllProduct(): Call<ProductResponse>

    //SERVICE
    @GET("transaction/service/{id}")
    fun getServiceTransaction(): Call<TransactionResponse>

    @GET("detail_service_transaction/{id_transaction}")
    fun getDetailServiceTransaction(@Path("id_transaction")id_transaction: String): Call<DetailServiceTransactionResponse>

}