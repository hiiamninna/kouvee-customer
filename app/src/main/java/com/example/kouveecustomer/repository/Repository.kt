package com.example.kouveecustomer.repository

import com.example.kouveecustomer.api.ApiClient
import com.example.kouveecustomer.model.*
import com.example.kouveemanagement.model.PetSizeResponse
import com.example.kouveemanagement.model.PetTypeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    //    PRODUCT
    fun getAllProduct(callback: ProductRepositoryCallback<ProductResponse>) {
        ApiClient().services.getAllProduct().enqueue(object : Callback<ProductResponse?> {
            override fun onFailure(call: Call<ProductResponse?>, t: Throwable) {
                callback.productFailed()
            }

            override fun onResponse(
                call: Call<ProductResponse?>,
                response: Response<ProductResponse?>
            ) {
                if (response.isSuccessful){
                    callback.productSuccess(response.body())
                }else{
                    callback.productFailed()
                }
            }
        })
    }

    fun getServiceTransaction(id: String, callback: TransactionRepositoryCallback<TransactionResponse>){
        ApiClient().services.getServiceTransaction(id).enqueue(object : Callback<TransactionResponse?>{
            override fun onFailure(call: Call<TransactionResponse?>, t: Throwable) {
                callback.transactionFailed()
            }
            override fun onResponse(
                call: Call<TransactionResponse?>,
                response: Response<TransactionResponse?>
            ) {
                if (response.isSuccessful){
                    callback.transactionSuccess(response.body())
                }else{
                    callback.transactionFailed()
                }
            }
        })
    }

    fun getDetailServiceTransaction(id_transaction: String, callback: DetailServiceTransactionRepositoryCallback<DetailServiceTransactionResponse>){
        ApiClient().services.getDetailServiceTransaction(id_transaction).enqueue(object : Callback<DetailServiceTransactionResponse?>{
            override fun onFailure(call: Call<DetailServiceTransactionResponse?>, t: Throwable) {
                callback.detailServiceTransactionFailed()
            }
            override fun onResponse(
                call: Call<DetailServiceTransactionResponse?>,
                response: Response<DetailServiceTransactionResponse?>
            ) {
                if (response.isSuccessful){
                    callback.detailServiceTransactionSuccess(response.body())
                }else{
                    callback.detailServiceTransactionFailed()
                }
            }
        })
    }

    fun getAllCustomerPet(callback: CustomerPetRepositoryCallback<CustomerPetResponse>){
        ApiClient().services.getAllCustomerPet().enqueue(object : Callback<CustomerPetResponse?> {
            override fun onFailure(call: Call<CustomerPetResponse?>, t: Throwable) {
                callback.customerPetFailed()
            }

            override fun onResponse(
                call: Call<CustomerPetResponse?>,
                response: Response<CustomerPetResponse?>
            ) {
                if (response.isSuccessful){
                    callback.customerPetSuccess(response.body())
                }else{
                    callback.customerPetFailed()
                }
            }
        })
    }

    fun getAllService(callback: ServiceRepositoryCallback<ServiceResponse>) {

        ApiClient().services.getAllService().enqueue(object : Callback<ServiceResponse?> {
            override fun onFailure(call: Call<ServiceResponse?>, t: Throwable) {
                callback.serviceFailed()
            }

            override fun onResponse(
                call: Call<ServiceResponse?>,
                response: Response<ServiceResponse?>
            ) {
                if (response.isSuccessful){
                    callback.serviceSuccess(response.body())
                }else{
                    callback.serviceFailed()
                }
            }
        })
    }

    //    PET TYPE
    fun getAllPetType(callback: PetTypeRepositoryCallback<PetTypeResponse>) {
        ApiClient().services.getAllPetType().enqueue(object : Callback<PetTypeResponse?> {
            override fun onFailure(call: Call<PetTypeResponse?>, t: Throwable) {
                callback.petTypeFailed(t.message.toString())
            }

            override fun onResponse(
                call: Call<PetTypeResponse?>,
                response: Response<PetTypeResponse?>
            ) {
                if (response.isSuccessful){
                    callback.petTypeSuccess(response.body())
                }else if (response.code() == 500){
                    callback.petTypeFailed("Show error..")
                }
            }
        })
    }

    //    PET SIZE
    fun getAllPetSize(callback: PetSizeRepositoryCallback<PetSizeResponse>) {

        ApiClient().services.getAllPetSize().enqueue(object : Callback<PetSizeResponse?> {
            override fun onFailure(call: Call<PetSizeResponse?>, t: Throwable) {
                callback.petSizeFailed(t.message.toString())
            }

            override fun onResponse(
                call: Call<PetSizeResponse?>,
                response: Response<PetSizeResponse?>
            ) {
                if (response.isSuccessful){
                    callback.petSizeSuccess(response.body())
                }else if (response.code() == 500){
                    callback.petSizeFailed("Show error..")
                }
            }
        })
    }
}