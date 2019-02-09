package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.pax.poslink.PaymentResponse
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.PaymentUtil
import com.tinle.emptyproject.data.TransactionRepo
import java.lang.Exception
import javax.inject.Inject

class PaymentVM @Inject constructor(
        private val paymentRepo:TransactionRepo,
        val executor:AppExecutor


):ViewModel() {

    init {

    }

    fun saveTransaction(resp:PaymentResponse) {
        executor.diskIO().execute {
            try {
                paymentRepo.saveTransAction(PaymentUtil.initPaymentTransaction(resp))
            }
            catch (e:Exception) {
                println(e.message)
            }

        }

    }

    fun getTransactionCount():Int{

        return 1 //paymentRepo.getTransActionCount()
    }


}