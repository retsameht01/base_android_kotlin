package com.tinle.checkin.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Checkin::class, PaymentTransaction::class, RewardsMember::class], version = 10)
abstract class AppDatabase : RoomDatabase() {
    abstract fun checkinDao(): CheckinDao
    abstract fun paymentTransactionDao():PaymentTransactionDao
    abstract fun customerDao():CustomerDao
}