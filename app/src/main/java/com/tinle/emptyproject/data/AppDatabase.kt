package com.tinle.emptyproject.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Checkin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun checkinDao(): CheckinDao
}