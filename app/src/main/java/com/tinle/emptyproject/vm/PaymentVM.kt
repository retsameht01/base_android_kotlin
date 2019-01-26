package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.pax.poslink.PaymentResponse
import com.tinle.emptyproject.core.PaymentUtil
import com.tinle.emptyproject.data.TransactionRepo
import javax.inject.Inject

class PaymentVM @Inject constructor(
        private val paymentRepo:TransactionRepo


):ViewModel() {

    fun saveTransaction(resp:PaymentResponse) {
        paymentRepo.saveTransAction(PaymentUtil.initPaymentTransaction(resp))
    }
}