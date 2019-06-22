package com.tinle.emptyproject.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RewardsMember(
        var Id: Int,
        var FirstName: String?,
        var LastName: String?,
        @PrimaryKey
        var Phone: String,
        var EmailAddress: String?,
        var RewardPointRate: Int?,
        var RewardPoints: Int,
        var LastPurchase: String?,
        var birthDate: String?,
        var SyncStatus: Int

)