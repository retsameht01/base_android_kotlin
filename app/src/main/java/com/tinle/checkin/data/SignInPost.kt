package com.tinle.checkin.data

data class SignInPost(val SignInTime:String,
                      val IsAppointment:Boolean,
                      val Phone:String,
                      val FirstName:String,
                      val LastName:String,
                      val Request:String
)