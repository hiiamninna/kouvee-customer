package com.example.kouveecustomer.repository

import com.example.kouveecustomer.api.ApiClient
import com.example.kouveecustomer.model.ProductResponse
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
}