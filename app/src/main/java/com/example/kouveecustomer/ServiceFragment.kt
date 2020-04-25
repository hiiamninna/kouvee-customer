package com.example.kouveecustomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kouveecustomer.MainActivity.Companion.pets
import com.example.kouveecustomer.adapter.DetailTransactionRecyclerViewAdapter
import com.example.kouveecustomer.model.DetailServiceTransaction
import com.example.kouveecustomer.model.DetailServiceTransactionResponse
import com.example.kouveecustomer.model.Transaction
import com.example.kouveecustomer.model.TransactionResponse
import com.example.kouveecustomer.presenter.DetailServiceTransactionPresenter
import com.example.kouveecustomer.presenter.DetailServiceTransactionView
import com.example.kouveecustomer.presenter.TransactionPresenter
import com.example.kouveecustomer.presenter.TransactionView
import com.example.kouveecustomer.repository.Repository
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * A simple [Fragment] subclass.
 */
class ServiceFragment : Fragment(), TransactionView, DetailServiceTransactionView {

    private lateinit var transaction: Transaction
    private lateinit var idTransaction: String
    private var detailServices: MutableList<DetailServiceTransaction> = mutableListOf()

    private var presenterT = TransactionPresenter(this, Repository())
    private var presenterD = DetailServiceTransactionPresenter(this, Repository())

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

    private fun setFirstVisible(){
        first_card.visibility = View.VISIBLE
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
            recyclerview.adapter = DetailTransactionRecyclerViewAdapter(detailServices, {}, MainActivity.services)
            context?.let { view?.let { itView -> CustomFun.successSnackBar(itView, it, "Yes, found it") } }
        }
    }

    override fun detailServiceTransactionFailed() {
        setFirstGone()
        setSuccessGone()
        setFailedVisible()
        context?.let { view?.let { itView -> CustomFun.failedSnackBar(itView, it, "Oops, try again") } }
    }

}
