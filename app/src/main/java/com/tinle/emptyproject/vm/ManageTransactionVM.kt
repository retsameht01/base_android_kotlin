package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.PaymentTransaction
import com.tinle.emptyproject.data.TransactionRepo
import javax.inject.Inject

class ManageTransactionVM @Inject constructor(
        val executor:AppExecutor,
        val transactionRepo: TransactionRepo

):ViewModel() {

    lateinit var transactions:List<PaymentTransaction>
    init {
        executor.diskIO().execute {
            transactions = transactionRepo.getTransactions()
        }
    }

    fun getTransActions():List<PaymentTransaction> {
        return transactions
    }

}