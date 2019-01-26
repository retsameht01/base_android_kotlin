package com.tinle.emptyproject.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PaymentTransactionDao {

    @Insert
    fun insertAll(vararg transaction: PaymentTransaction)

    @Query("SELECT Id FROM PaymentTransaction ORDER BY Id DESC LIMIT 1 ")
    fun getLastTransActionId():Int


}