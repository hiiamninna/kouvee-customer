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

interface ServiceRepositoryCallback<ServiceResponse> {
    fun serviceSuccess(data: ServiceResponse?)
    fun serviceFailed()
}

interface CustomerPetRepositoryCallback<CustomerPetResponse> {
    fun customerPetSuccess(data: CustomerPetResponse?)
    fun customerPetFailed()
}

interface PetSizeRepositoryCallback<PetSizeResponse> {
    fun petSizeSuccess(data: PetSizeResponse?)
    fun petSizeFailed(data: String)
}

interface PetTypeRepositoryCallback<PetTypeResponse> {
    fun petTypeSuccess(data: PetTypeResponse?)
    fun petTypeFailed(data: String)
}
