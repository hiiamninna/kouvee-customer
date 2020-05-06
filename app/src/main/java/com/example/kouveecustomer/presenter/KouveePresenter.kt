package com.example.kouveecustomer.presenter

import com.example.kouveecustomer.model.*
import com.example.kouveecustomer.repository.*
import com.example.kouveemanagement.model.PetSizeResponse
import com.example.kouveemanagement.model.PetTypeResponse

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

    fun getServiceTransaction(id: String){
        view.showTransactionLoading()
        repository.getServiceTransaction(id, object : TransactionRepositoryCallback<TransactionResponse> {
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

class CustomerPetPresenter(private val view: CustomerPetView, private val repository: Repository){

    fun getAllCustomerPet(){
        view.showCustomerPetLoading()
        repository.getAllCustomerPet(object : CustomerPetRepositoryCallback<CustomerPetResponse> {
            override fun customerPetSuccess(data: CustomerPetResponse?) {
                view.customerPetSuccess(data)
                view.hideCustomerPetLoading()
            }
            override fun customerPetFailed() {
                view.customerPetFailed()
                view.hideCustomerPetLoading()
            }
        })
    }

}

class ServicePresenter(private val view: ServiceView, private val repository: Repository){

    fun getAllService(){
        view.showServiceLoading()
        repository.getAllService(object : ServiceRepositoryCallback<ServiceResponse> {
            override fun serviceSuccess(data: ServiceResponse?) {
                view.serviceSuccess(data)
                view.hideServiceLoading()
            }
            override fun serviceFailed() {
                view.serviceFailed()
                view.hideServiceLoading()
            }
        })
    }
}

//PET SIZE
class PetSizePresenter(private val view: PetSizeView, private val repository: Repository){

    fun getAllPetSize(){
        view.showPetSizeLoading()
        repository.getAllPetSize(object : PetSizeRepositoryCallback<PetSizeResponse> {
            override fun petSizeSuccess(data: PetSizeResponse?) {
                view.petSizeSuccess(data)
                view.hidePetSizeLoading()
            }
            override fun petSizeFailed(data: String) {
                view.petSizeFailed(data)
                view.hidePetSizeLoading()
            }
        })
    }
}

//PET TYPE
class PetTypePresenter(private val view: PetTypeView, private val repository: Repository){

    fun getAllPetType(){
        view.showPetTypeLoading()
        repository.getAllPetType(object : PetTypeRepositoryCallback<PetTypeResponse> {
            override fun petTypeSuccess(data: PetTypeResponse?) {
                view.petTypeSuccess(data)
                view.hidePetTypeLoading()
            }
            override fun petTypeFailed(data: String) {
                view.petTypeFailed(data)
                view.hidePetTypeLoading()
            }
        })
    }
}
