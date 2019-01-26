package com.tinle.emptyproject.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Checkin::class, PaymentTransaction::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun checkinDao(): CheckinDao
    abstract fun paymentTransactionDao():PaymentTransactionDao
}