package com.tinle.emptyproject.data

//"SignInTime": "2018-12-22T21:40:19.4670884+00:00",
data class SignIn(
        val Id:Int,
        val SignInTime:String,
        val IsAppointment:Boolean,
        val Status:String,
        val CustomerId:Int,
        val Phone:String,
        val FirstName:String,
        val LastName:String,
        val Request:String

) {


}