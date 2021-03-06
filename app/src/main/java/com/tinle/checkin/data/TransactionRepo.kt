package com.tinle.checkin.data

import com.tinle.checkin.core.AppExecutor
import javax.inject.Inject

class TransactionRepo @Inject constructor(

        private val transDao:PaymentTransactionDao,
        private val appExecutor: AppExecutor
){
     fun saveTransAction(paymentTrans:PaymentTransaction) {
        appExecutor.diskIO().execute{
            transDao.insertAll(paymentTrans)
        }

    }

    fun getTransactions():List<PaymentTransaction> {
        return transDao.getAllTransactions()
    }

    fun getTransActionCount():Int {
        return transDao.getTransActionCount()
    }

}