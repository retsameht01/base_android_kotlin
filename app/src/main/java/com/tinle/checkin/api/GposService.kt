package com.tinle.checkin.api

import android.util.Log
import com.tinle.checkin.core.DateUtil
import com.tinle.checkin.core.IEncryption
import com.tinle.checkin.data.RewardsMember
import com.tinle.checkin.data.SignInPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.tinle.checkin.core.PreferenceStore
import com.tinle.checkin.data.SignInDTO
import com.tinle.checkin.data.SignUp
import javax.inject.Inject


class GposService @Inject constructor(
        prefStore:PreferenceStore,
        encryptService:IEncryption,
        dateUtil:DateUtil

) {
    private val BASE_URL = "https://www.gposdev.com/%s/api/"
    private var retrofit:Retrofit
    private var api:GPOSApi
    private var authString:String
    private var token:String
    private val TAG = "gposService"

    init {
        val apiCode = prefStore.getAPI()
        val URL = String.format(BASE_URL, apiCode)
        retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api  = retrofit.create(GPOSApi::class.java)
        var tokenKey = android.util.Base64.encodeToString("admin:6786716888".toByteArray(Charsets.UTF_8), android.util.Base64.DEFAULT)
        tokenKey = tokenKey.replace("\n", "")
        authString =  "Basic $tokenKey"
        token = encryptService.encryptHmacSha1(tokenKey, dateUtil.getUTCdateAsString())
        token = token.replace("\n", "")
        Log.d(TAG, "init complete")
    }

    fun getCustomerInfo(phone:String, callback: Callback<RewardsMember>) {
        val call = api.getCustomer(authString,"application/json",token, phone)
        call.enqueue(callback)
    }

    fun getCustomers(callback: Callback<List<RewardsMember>>){
        val call = api.getCustomers(authString, token)
        call.enqueue(callback)
    }

    fun signIn(data:SignInDTO, callback: Callback<String>) {
        val call = api.signin(authString, token, data)
        call.enqueue(callback)
    }

    fun signUp(data:SignUp, callback: Callback<String>) {
        val call = api.signup(authString, token, data)
        call.enqueue(callback)
    }

}


interface GPOSApi{
    @GET("/categories")
    fun getCategories():Call<List<Object>>

    @POST("/checkin")
    fun doCheckIn(@Header("Authorization") authToken:String, @Body checkin:SignInPost):Call<String>

    @GET("customers/{phone}")
    fun getCustomer(@Header("Authorization")authToken: String, @Header("Content-Type")content_type:String ,
                    @Header("token")token:String, @Path("phone") phone:String):Call<RewardsMember>

    @GET("customers")
    fun getCustomers(@Header("Authorization")authToken: String, @Header("token")token:String):Call<List<RewardsMember>>

    @POST("signins")
    fun signin(@Header("Authorization")authToken: String, @Header("token")token:String, @Body data:SignInDTO):Call<String>

    @POST("customers")
    fun signup(@Header("Authorization")authToken: String, @Header("token")token: String, @Body data:SignUp):Call<String>


}