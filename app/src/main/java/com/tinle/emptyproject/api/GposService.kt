package com.tinle.emptyproject.api

import android.util.Log
import com.tinle.emptyproject.core.DateUtil
import com.tinle.emptyproject.core.IEncryption
import com.tinle.emptyproject.data.CustomerInfo
import com.tinle.emptyproject.data.SignInPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.tinle.emptyproject.core.PreferenceStore
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
        token = encryptService.encryptHmacSha1(tokenKey, dateUtil.getUTCdatetimeAsString())
        token = token.replace("\n", "")
        Log.d(TAG, "init complete")
    }

    fun getCustomerInfo(phone:String, callback: Callback<CustomerInfo>) {
        val call = api.getCustomer(authString, token, phone)
        call.enqueue(callback)
    }

}

interface GPOSApi{
    @GET("/categories")
    fun getCategories():Call<List<Object>>

    @POST("/checkin")
    fun doCheckIn(@Header("Authorization") authToken:String, @Body checkin:SignInPost):Call<String>

    @GET("customers/{phone}")
    fun getCustomer(@Header("Authorization")authToken: String, @Header("token")token:String, @Path("phone") phone:String):Call<CustomerInfo>

}