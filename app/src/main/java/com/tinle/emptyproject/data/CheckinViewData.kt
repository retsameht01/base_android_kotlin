package com.tinle.emptyproject.data

import java.io.Serializable
data class CheckinViewData (
        val phone:String,
        val firstName:String,
        val lastName:String,
        val email:String,
        val timeStamp:String,
        val rewardPoints:String

):Serializable {
}