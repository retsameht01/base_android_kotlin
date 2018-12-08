package com.tinle.emptyproject.api

import com.tinle.emptyproject.data.CustomerInfo
import com.tinle.emptyproject.data.Post
import com.tinle.emptyproject.data.SignInPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.tinle.emptyproject.core.PreferenceStore
import javax.inject.Inject


class ApiHandler @Inject constructor(
        prefStore:PreferenceStore

) {
    private val BASE_URL = "https://www.gposdev.com/%s/api/"
    private  var retrofit:Retrofit
    private  var api:MyApi
    private  var authString:String

    init {
        val apiCode = prefStore.getAPI()
        val URL = String.format(BASE_URL, apiCode)
        retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api  = retrofit.create(MyApi::class.java)
        authString =  "Basic "  + android.util.Base64.encodeToString("admin:6786716888".toByteArray(Charsets.UTF_8), android.util.Base64.DEFAULT)
        authString = authString.replace("\n","")
    }



    fun getPosts(callback:Callback<List<Post>>){
        val call = api.getPosts()
        call.enqueue(callback)
    }

    fun getCustomerInfo(phone:String, callback: Callback<CustomerInfo>) {
        val call = api.getCustomer(authString, phone)
        call.enqueue(callback)
    }

}

interface MyApi{
    @GET("/promos")
    fun getPosts(): Call<List<Post>>

    @GET("/categories")
    fun getCategories():Call<List<Object>>

    @POST("/checkin")
    fun doCheckIn(@Header("Authorization") authToken:String, @Body checkin:SignInPost):Call<String>

    @GET("customers/{phone}")
    fun getCustomer(@Header("Authorization")authToken: String, @Path("phone") phone:String):Call<CustomerInfo>

}