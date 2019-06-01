package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import com.pax.poslink.ManageRequest
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.PaymentTransaction
import com.tinle.emptyproject.data.TransactionModel
import com.tinle.emptyproject.data.TransactionRepo
import javax.inject.Inject

class ManageTransactionVM @Inject constructor(
        val executor:AppExecutor,
        val transactionRepo: TransactionRepo

):ViewModel() {
    val transactionsData = listOf("Init", "Reset", "Total Summary", "Close Batch", "Void", "Adjust Tip", "Print")
    var gson:Gson
    lateinit var transactions:List<PaymentTransaction>
    init {
        executor.diskIO().execute {
            transactions = transactionRepo.getTransactions()
        }
        gson = Gson()
    }

    fun getTransActions():List<PaymentTransaction> {
        return transactions
    }

    fun getInitData():String {
        val req = ManageRequest()
        req.TransType =  req.ParseTransType("INIT")
        return gson.toJson(req, ManageRequest::class.java)
    }

    fun getResetData():String {
        val req = ManageRequest()
        req.TransType =  req.ParseTransType("RESET")
        return gson.toJson(req, ManageRequest::class.java)
    }

    fun getPrintData():String {
        val req = ManageRequest()
        req.TransType = 29
        req.PrintCopy = "1"
        req.PrintData = "Some data"
        return gson.toJson(req, ManageRequest::class.java)
    }

    fun getViewData():List<TransactionModel> {
        val data = mutableListOf<TransactionModel>()
        for (trans in transactionsData) {
            data.add(TransactionModel(trans, 1))
        }
        return data
    }

}