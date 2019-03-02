package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import com.pax.poslink.ManageRequest
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.PaymentTransaction
import com.tinle.emptyproject.data.TransactionRepo
import javax.inject.Inject

class ManageTransactionVM @Inject constructor(
        val executor:AppExecutor,
        val transactionRepo: TransactionRepo

):ViewModel() {

    lateinit var gson:Gson
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

}