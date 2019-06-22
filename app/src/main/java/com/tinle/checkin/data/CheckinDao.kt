package com.tinle.checkin.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CheckinDao {
    @Insert
    fun insertAll(vararg checkins: Checkin)

    @Query("SELECT * FROM Checkin")
    fun getAllCheckins(): List<Checkin>

    @Query("SELECT * FROM Checkin WHERE checkinDate = :date AND checkoutTime IS NULL")
    fun getCheckinForDate(date:String): List<Checkin>


    @Query("SELECT DISTINCT phone FROM Checkin")
    fun getAllPhones():List<String>

    @Query("UPDATE Checkin SET checkoutTime = :checkoutTime WHERE phone = :phone")
    fun setCheckout(checkoutTime: String, phone:String)

    @Query("SELECT * FROM Checkin WHERE phone = :phone AND checkoutTime IS NULL")
    fun getOpenCheckin(phone: String):List<Checkin>

}