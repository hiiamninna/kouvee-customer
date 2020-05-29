package com.example.kouveecustomer

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kouveecustomer.adapter.DetailTransactionRecyclerViewAdapter
import com.example.kouveecustomer.model.*
import com.example.kouveecustomer.presenter.*
import com.example.kouveecustomer.repository.Repository
import com.example.kouveemanagement.model.PetSize
import com.example.kouveemanagement.model.PetSizeResponse
import com.example.kouveemanagement.model.PetType
import com.example.kouveemanagement.model.PetTypeResponse
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * A simple [Fragment] subclass.
 */
class ServiceFragment : Fragment(), TransactionView, DetailServiceTransactionView, ServiceView, CustomerPetView, PetTypeView, PetSizeView {

    private lateinit var transaction: Transaction
    private lateinit var idTransaction: String
    private var detailServices: MutableList<DetailServiceTransaction> = mutableListOf()
    private var services: MutableList<Service> = mutableListOf()
    private var pets: MutableList<CustomerPet> = mutableListOf()
    private var petSizes: MutableList<PetSize> = mutableListOf()
    private var petTypes: MutableList<PetType> = mutableListOf()

    private var presenterT = TransactionPresenter(this, Repository())
    private var presenterD = DetailServiceTransactionPresenter(this, Repository())
    private lateinit var presenterS: ServicePresenter
    private lateinit var presenterP: CustomerPetPresenter
    private lateinit var presenterSize: PetSizePresenter
    private lateinit var presenterType: PetTypePresenter
    private var alertDialog: AlertDialog? = null

    companion object {
        fun newInstance() = ServiceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenterSize = PetSizePresenter(this, Repository())
        presenterSize.getAllPetSize()
        presenterType = PetTypePresenter(this, Repository())
        presenterType.getAllPetType()
        presenterP = CustomerPetPresenter(this, Repository())
        presenterP.getAllCustomerPet()
        presenterS = ServicePresenter(this, Repository())
        presenterS.getAllService()
        if (!CustomFun.verifiedNetwork(requireActivity())) warningDialog()
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                idTransaction = query.toString()
                presenterT.getServiceTransaction(query.toString())
                presenterD.getDetailServiceTransaction(query.toString())
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setData(transaction: Transaction){
        id_transaction.text = transaction.id
        var type = ""
        var nameOfPet  = ""
        if (transaction.id_customer_pet.equals("1")){
            pet.text = "Guest"
        }else{
            for (inputPet in pets){
                if (transaction.id_customer_pet == inputPet.id){
                    nameOfPet = inputPet.name.toString()
                    type = inputPet.id_type.toString()
                }
            }
            for (t in petTypes){
                if (t.id.equals(type)){
                    type = t.name.toString()
                }
            }
            pet.text = "$nameOfPet ( $type )"
        }
        status.text = transaction.status
        val discountFirst = transaction.discount.toString()
        if (discountFirst == ""){
            discount.text = CustomFun.changeToRp(0.00)
        }else{
            discount.text = CustomFun.changeToRp(discountFirst.toDouble())
        }
        val price = transaction.total_price.toString()
        total_price.text = CustomFun.changeToRp(price.toDouble())
        if (transaction.payment.equals("0")){
            payment.text = "Not Yet Paid Off"
        }else{
            payment.text = "Paid Off"
        }
        cs.text = transaction.last_cs.toString()
        if (!transaction.last_cr.isNullOrEmpty()) {
            cr.text = transaction.last_cr.toString()
        } else {
            cr.text = "-"
        }
        search_view.setQuery("", false)
    }

    private fun setFirstGone(){
        first_card.visibility = View.GONE
    }

    private fun setFailedVisible(){
        failed_card.visibility = View.VISIBLE
    }

    private fun setFailedGone(){
        failed_card.visibility = View.GONE
    }

    private fun setSuccessVisible(){
        success_card.visibility = View.VISIBLE
        recyclerview.visibility = View.VISIBLE
    }

    private fun setSuccessGone(){
        success_card.visibility = View.GONE
        recyclerview.visibility = View.GONE
    }

    override fun showTransactionLoading() {
        setFirstGone()
        setFailedGone()
        setSuccessGone()
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideTransactionLoading() {
        progress_circular.visibility = View.GONE
    }

    override fun transactionSuccess(data: TransactionResponse?) {
        setFirstGone()
        val temp = data?.transactions
        if (temp?.size == 0){
            setSuccessGone()
            setFailedVisible()
            CustomFun.failedSnackBar(requireView(), requireContext(), "Oops, try again")
        }else{
            setFailedGone()
            setSuccessVisible()
            transaction = temp?.get(0)!!
            setData(transaction)
            CustomFun.successSnackBar(requireView(), requireContext(), "Yes, found it")
        }
    }

    override fun transactionFailed() {
        setFirstGone()
        setSuccessGone()
        setFailedVisible()
    }

    override fun showDetailServiceTransactionLoading() {
        setFirstGone()
        setFailedGone()
        setSuccessGone()
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideDetailServiceTransactionLoading() {
        progress_circular.visibility = View.GONE
    }

    override fun detailServiceTransactionSuccess(data: DetailServiceTransactionResponse?) {
        setFirstGone()
        setFailedGone()
        setSuccessVisible()
        val temp: List<DetailServiceTransaction> = data?.detailServiceTransactions ?: emptyList()
        if (temp.isEmpty()){
            CustomFun.failedSnackBar(requireView(), requireContext(), "Oops, try again")
        }else{
            detailServices.clear()
            detailServices.addAll(temp)
            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.adapter = DetailTransactionRecyclerViewAdapter(detailServices, {}, services, petSizes)
            CustomFun.successSnackBar(requireView(), requireContext(), "Yes, found it")
        }
    }

    override fun detailServiceTransactionFailed() {
        setFirstGone()
        setSuccessGone()
        setFailedVisible()
        CustomFun.failedSnackBar(requireView(), requireContext(), "Oops, try again")
    }

    override fun showServiceLoading() {
    }

    override fun hideServiceLoading() {
    }

    override fun serviceSuccess(data: ServiceResponse?) {
        if (alertDialog != null){
            alertDialog?.dismiss()
        }
        val temp: List<Service> = data?.services ?: emptyList()
        if (temp.isNotEmpty()){
            services.clear()
            services.addAll(temp)
        }
    }

    override fun serviceFailed() {
        warningDialog()
        CustomFun.failedSnackBar(container, requireContext(), "Service failed")
    }

    override fun showCustomerPetLoading() {
    }

    override fun hideCustomerPetLoading() {
    }

    override fun customerPetSuccess(data: CustomerPetResponse?) {
        if (alertDialog != null){
            alertDialog?.dismiss()
        }
        val temp: List<CustomerPet> = data?.customerpets ?: emptyList()
        if (temp.isNotEmpty()){
            pets.clear()
            pets.addAll(temp)
        }
    }

    override fun customerPetFailed() {
        warningDialog()
        CustomFun.failedSnackBar(container, requireContext(), "Pet failed")
    }

    private fun warningDialog(){
        alertDialog = AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.alert)
            .setTitle("Warning message")
            .setMessage("We needs internet connection to get some data, so make sure it run clearly.")
            .setNeutralButton("EXIT"){ _: DialogInterface, _: Int ->
                requireActivity().finishAffinity()
            }
            .setPositiveButton("TRY AGAIN"){ _: DialogInterface, _: Int ->
                presenterSize.getAllPetSize()
                presenterType.getAllPetType()
                presenterS.getAllService()
                presenterP.getAllCustomerPet()
            }
            .setCancelable(false)
            .show()
    }

    override fun showPetTypeLoading() {
    }

    override fun hidePetTypeLoading() {
    }

    override fun petTypeSuccess(data: PetTypeResponse?) {
        if (alertDialog != null){
            alertDialog?.dismiss()
        }
        val temp: List<PetType> = data?.pettype ?: emptyList()
        if (temp.isNotEmpty()){
            petTypes.clear()
            petTypes.addAll(temp)
        }
    }

    override fun petTypeFailed(data: String) {
        warningDialog()
        CustomFun.failedSnackBar(container, requireContext(), "Type failed")
    }

    override fun showPetSizeLoading() {
    }

    override fun hidePetSizeLoading() {
    }

    override fun petSizeSuccess(data: PetSizeResponse?) {
        if (alertDialog != null){
            alertDialog?.dismiss()
        }
        val temp: List<PetSize> = data?.petsize ?: emptyList()
        if (temp.isNotEmpty()){
            petSizes.clear()
            petSizes.addAll(temp)
        }
    }

    override fun petSizeFailed(data: String) {
        warningDialog()
        CustomFun.failedSnackBar(container, requireContext(), "Size failed")
    }


}
