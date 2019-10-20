package com.tinle.checkin.vm

import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import com.pax.poslink.ManageRequest
import com.tinle.checkin.core.AppExecutor
import com.tinle.checkin.data.PaymentTransaction
import com.tinle.checkin.data.TransactionModel
import com.tinle.checkin.data.TransactionRepo
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
        req.PrintData = "\\\$Header\\C\\3Menu\n" +
                "Name\\CNumber\\RPrice\n" +
                "\\1Salad\\1\\CX 2\\1\\R\$5.00\n" +
                "\\1Fish\\1\\CX 3\\1\\R\$15.00\n" +
                "\\1Bread\\1\\CX 4\\1\\R\$2.50\n" +
                "Total\\C9\\R\$22.50\n" +
                "\n" +
                "\\LDate\\R\\\$Date\n" +
                "\\LTime\\R\\\$Time\n" +
                "\\LSN\\R\\\$SN#\n" +
                "\\\$Disclaimer\\\$Trailerchewcopyrightbubblecoachfolkwordfolkloresalmonfolklorecoachcrossingcoachfolkloreprintnoiseskeletonshiverskeleton"

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