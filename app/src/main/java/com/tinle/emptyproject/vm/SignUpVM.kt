package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.api.GposService
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.DateUtil
import com.tinle.emptyproject.data.RewardsMember
import com.tinle.emptyproject.data.SignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import javax.inject.Inject

class SignUpVM @Inject constructor(
        private val executor:AppExecutor,
        private val gposService:GposService,
        private val dateUtil: DateUtil

):ViewModel() {

    private lateinit var signupListener: SignupListener

    fun signUp(firstName:String, lastName:String, phone:String, email:String, listener: SignupListener):String {
        if(!isValidFirstName(firstName)) {
            return "Invalid Firstname"
        }
        if(!isValidLastName(lastName)) {
            return "Invalid LastName"
        }
        if(!isEmailValid(email)) {
            return "Invalid Email"
        }
        if(!isPhoneValid(phone)) {
            return "Invalid Phone"
        }
        signupListener = listener
        doSignup(firstName, lastName, phone, email)
        return ""
    }

    private fun doSignup(firstName:String, lastName:String, phone:String, email:String) {
        val data = SignUp(firstName, lastName, phone, email, dateUtil.getUTCDateTimestamp())
        gposService.signUp(data, object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                signupListener.onComplete(false, t.localizedMessage)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.body() != null) {
                    signupListener.onComplete(true, "${response.body()}")
                } else {
                    signupListener.onComplete(false, response.message())
                }

            }
        })
    }

    private fun isValidFirstName(fName:String):Boolean{
        return !fName.isNullOrEmpty()
    }

    private fun isValidLastName(lName:String):Boolean {
        return !lName.isNullOrEmpty()
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPhoneValid(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }

    interface SignupListener {

        fun onComplete(boolean: Boolean, msg:String)
    }

}