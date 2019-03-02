package com.tinle.emptyproject.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RewardsMember(
        @PrimaryKey(autoGenerate = true)
        var Id:Int,
        var FirstName:String?,
        var LastName:String?,
        var Phone:String,
        var EmailAddress:String?,
        var RewardPointRate:Int?,
        var RewardPoints:Float?,
        var LastPurchase:String?

)