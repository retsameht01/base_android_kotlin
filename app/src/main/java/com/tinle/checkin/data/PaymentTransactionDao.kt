package com.tinle.checkin.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PaymentTransactionDao {

    @Insert
    fun insertAll(vararg transaction: PaymentTransaction)

    @Query("SELECT COUNT(*) FROM PaymentTransaction")
    fun getTransActionCount():Int

    @Query("SELECT * FROM PaymentTransaction")
    fun getAllTransactions():List<PaymentTransaction>


}