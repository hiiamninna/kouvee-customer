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
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * A simple [Fragment] subclass.
 */
class ServiceFragment : Fragment(), TransactionView, DetailServiceTransactionView, ServiceView, CustomerPetView {

    private lateinit var transaction: Transaction
    private lateinit var idTransaction: String
    private var detailServices: MutableList<DetailServiceTransaction> = mutableListOf()
    private var services: MutableList<Service> = mutableListOf()
    private var pets: MutableList<CustomerPet> = mutableListOf()

    private var presenterT = TransactionPresenter(this, Repository())
    private var presenterD = DetailServiceTransactionPresenter(this, Repository())
    private lateinit var presenterS: ServicePresenter
    private lateinit var presenterP: CustomerPetPresenter
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
        for (i in pets.indices){
            if (transaction.id_customer_pet == pets[i].id){
                pet.text = pets[i].name
            }
        }
        status.text = transaction.status
        val price = transaction.total_price.toString()
        total_price.text = CustomFun.changeToRp(price.toDouble())
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
    }

    override fun hideTransactionLoading() {
    }

    override fun transactionSuccess(data: TransactionResponse?) {
        setFirstGone()
        val temp = data?.transactions
        if (temp?.size == 0){
            setSuccessGone()
            setFailedVisible()
            context?.let { view?.let { itView -> CustomFun.failedSnackBar(itView, it, "Oops, try again") } }
        }else{
            setFailedGone()
            setSuccessVisible()
            transaction = temp?.get(0)!!
            setData(transaction)
            context?.let { view?.let { itView -> CustomFun.successSnackBar(itView, it, "Yes, found it") } }
        }
    }

    override fun transactionFailed() {
        setFirstGone()
        setSuccessGone()
        setFailedVisible()
    }

    override fun showDetailServiceTransactionLoading() {
    }

    override fun hideDetailServiceTransactionLoading() {
    }

    override fun detailServiceTransactionSuccess(data: DetailServiceTransactionResponse?) {
        setFirstGone()
        setFailedGone()
        setSuccessVisible()
        val temp: List<DetailServiceTransaction> = data?.detailServiceTransactions ?: emptyList()
        if (temp.isEmpty()){
            context?.let { view?.let { itView -> CustomFun.warningSnackBar(itView, it, "Oops, try again") } }
        }else{
            detailServices.clear()
            detailServices.addAll(temp)
            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.adapter = DetailTransactionRecyclerViewAdapter(detailServices, {}, services)
            context?.let { view?.let { itView -> CustomFun.successSnackBar(itView, it, "Yes, found it") } }
        }
    }

    override fun detailServiceTransactionFailed() {
        setFirstGone()
        setSuccessGone()
        setFailedVisible()
        context?.let { view?.let { itView -> CustomFun.failedSnackBar(itView, it, "Oops, try again") } }
    }

    override fun showServiceLoading() {
    }

    override fun hideServiceLoading() {
    }

    override fun serviceSuccess(data: ServiceResponse?) {
        if (alertDialog != null){
            alertDialog?.dismiss()
        }
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
        context?.let { CustomFun.failedSnackBar(container, it, "Service failed") }
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
        context?.let { CustomFun.failedSnackBar(container, it, "Pet failed") }
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
                presenterS.getAllService()
                presenterP.getAllCustomerPet()
            }
            .setCancelable(false)
            .show()
    }


}
