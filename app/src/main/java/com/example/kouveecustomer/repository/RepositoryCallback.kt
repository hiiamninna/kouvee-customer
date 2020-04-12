package com.example.kouveecustomer.repository

interface ProductRepositoryCallback<ProductResponse> {
    fun productSuccess(data: ProductResponse?)
    fun productFailed()
}

interface TransactionRepositoryCallback<TransactionResponse>{
    fun transactionSuccess(data: TransactionResponse?)
    fun transactionFailed()
}

interface DetailServiceTransactionRepositoryCallback<DetailServiceTransactionResponse>{
    fun detailServiceTransactionSuccess(data: DetailServiceTransactionResponse?)
    fun detailServiceTransactionFailed()
}
