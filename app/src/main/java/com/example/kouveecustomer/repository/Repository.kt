package com.example.kouveecustomer.repository

import com.example.kouveecustomer.api.ApiClient
import com.example.kouveecustomer.model.DetailServiceTransactionResponse
import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.model.TransactionResponse
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

    fun getServiceTransaction(callback: TransactionRepositoryCallback<TransactionResponse>){
        ApiClient().services.getServiceTransaction().enqueue(object : Callback<TransactionResponse?>{
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
}