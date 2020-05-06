package com.example.kouveecustomer.api

import com.example.kouveecustomer.model.*
import com.example.kouveemanagement.model.PetSizeResponse
import com.example.kouveemanagement.model.PetTypeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    //PRODUCT
    @GET("product")
    fun getAllProduct(): Call<ProductResponse>

    //SERVICE
    @GET("transaction/service/{id}")
    fun getServiceTransaction(@Path("id")id: String): Call<TransactionResponse>

    @GET("detail_service_transaction/{id_transaction}")
    fun getDetailServiceTransaction(@Path("id_transaction")id_transaction: String): Call<DetailServiceTransactionResponse>

    //ADDING
    @GET("customer_petAll")
    fun getAllCustomerPet(): Call<CustomerPetResponse>

    @GET("serviceAll")
    fun getAllService(): Call<ServiceResponse>

    //PET SIZE
    @GET("pet_sizeAll")
    fun getAllPetSize(): Call<PetSizeResponse>

    //PET TYPE
    @GET("pet_typeAll")
    fun getAllPetType(): Call<PetTypeResponse>
}