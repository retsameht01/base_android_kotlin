package com.tinle.emptyproject.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Checkin(
        val checkinTime:String,
        val phone:String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var checkoutTime:String? = null
}