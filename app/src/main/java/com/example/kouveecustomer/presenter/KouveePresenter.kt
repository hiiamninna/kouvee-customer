package com.example.kouveecustomer.presenter

import com.example.kouveecustomer.model.DetailServiceTransactionResponse
import com.example.kouveecustomer.model.ProductResponse
import com.example.kouveecustomer.model.TransactionResponse
import com.example.kouveecustomer.repository.DetailServiceTransactionRepositoryCallback
import com.example.kouveecustomer.repository.ProductRepositoryCallback
import com.example.kouveecustomer.repository.Repository
import com.example.kouveecustomer.repository.TransactionRepositoryCallback

//PRODUCT
class ProductPresenter(private val view : ProductView, private val repository: Repository){

    fun getAllProduct(){
        view.showProductLoading()
        repository.getAllProduct(object : ProductRepositoryCallback<ProductResponse> {
            override fun productSuccess(data: ProductResponse?) {
                view.productSuccess(data)
                view.hideProductLoading()
            }
            override fun productFailed() {
                view.productFailed()
                view.hideProductLoading()
            }
        })
    }
}

class TransactionPresenter(private val view: TransactionView, private val repository: Repository){

    fun getServiceTransaction(){
        view.showTransactionLoading()
        repository.getServiceTransaction(object : TransactionRepositoryCallback<TransactionResponse> {
            override fun transactionSuccess(data: TransactionResponse?) {
                view.transactionSuccess(data)
                view.hideTransactionLoading()
            }
            override fun transactionFailed() {
                view.transactionFailed()
                view.hideTransactionLoading()
            }
        })
    }
}

class DetailServiceTransactionPresenter(private val view: DetailServiceTransactionView, private val repository: Repository){

    fun getDetailServiceTransaction(id_transaction: String){
        view.showDetailServiceTransactionLoading()
        repository.getDetailServiceTransaction(id_transaction, object : DetailServiceTransactionRepositoryCallback<DetailServiceTransactionResponse> {
            override fun detailServiceTransactionSuccess(data: DetailServiceTransactionResponse?) {
                view.detailServiceTransactionSuccess(data)
                view.hideDetailServiceTransactionLoading()
            }
            override fun detailServiceTransactionFailed() {
                view.detailServiceTransactionFailed()
                view.hideDetailServiceTransactionLoading()
            }
        })
    }

}
