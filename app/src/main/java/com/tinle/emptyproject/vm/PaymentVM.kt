package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.pax.poslink.PaymentResponse
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.PaymentUtil
import com.tinle.emptyproject.data.CustomerRepo
import com.tinle.emptyproject.data.TransactionRepo
import java.lang.Exception
import javax.inject.Inject

class PaymentVM @Inject constructor(
        private val paymentRepo:TransactionRepo,
        private val customerRepo: CustomerRepo,
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

    fun updateCustomerRewards(rewards:Int, phone:String) {
        executor.diskIO().execute {
            val customer = customerRepo.getCustomer(phone)
            if ( customer != null) {
                customer.RewardPoints = customer.RewardPoints + rewards
                customerRepo.addCustomer(customer)

            }
        }
    }

    fun getTransactionCount():Int{

        return 1 //paymentRepo.getTransActionCount()
    }


}