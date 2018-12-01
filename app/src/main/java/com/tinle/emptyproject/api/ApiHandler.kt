package com.tinle.emptyproject.api

import com.tinle.emptyproject.data.CustomerInfo
import com.tinle.emptyproject.data.Post
import com.tinle.emptyproject.data.SignInPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import android.R.attr.password
import android.util.Base64
import java.util.Base64.getEncoder



class ApiHandler {
    private val BASE_URL = "https://www.gposdev.com/20002/api/"
    // "https://jsonplaceholder.typicode.com"
    private lateinit var retrofit:Retrofit
    private lateinit var api:MyApi
    private lateinit var authString:String

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api  = retrofit.create(MyApi::class.java)
        authString =  "Basic "  + android.util.Base64.encodeToString("admin:6786716888".toByteArray(Charsets.UTF_8), android.util.Base64.DEFAULT)
        authString = authString.replace("\n","")
    }

    fun getTodo(callback: Callback<List<Any>>) {
        val call = api.getPosts()
        //call.enqueue(callback)
    }

    fun getPosts(callback:Callback<List<Post>>){
        val call = api.getPosts()
        call.enqueue(callback)
    }

    fun checkin(signinData:SignInPost, callback:Callback<String>){
        val call = api.doCheckIn(authString, signinData)
        call.enqueue(callback)
    }

    fun getCustomerInfo(phone:String, callback: Callback<CustomerInfo>) {
        val call = api.getCustomer(authString, phone)
        call.enqueue(callback)
    }

    private fun getToken():String{

        return ""
    }

}

interface MyApi{
    @GET("/posts")
    fun getPosts(): Call<List<Post>>

    @GET("/categories")
    fun getCategories():Call<List<Object>>

    @POST("/checkin")
    fun doCheckIn(@Header("Authorization") authToken:String, @Body checkin:SignInPost):Call<String>

    @GET("customers/{phone}")
    fun getCustomer(@Header("Authorization")authToken: String, @Path("phone") phone:String):Call<CustomerInfo>

}