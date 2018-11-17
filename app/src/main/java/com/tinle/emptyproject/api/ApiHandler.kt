package com.tinle.emptyproject.api

import com.tinle.emptyproject.data.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ApiHandler {
    private val BASE_URL = "https://jsonplaceholder.typicode.com"
    private lateinit var retrofit:Retrofit
    private lateinit var api:MyApi
    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api  = retrofit.create(MyApi::class.java)
    }

    fun getTodo(callback: Callback<List<Any>>) {
        val call = api.getPosts()
        //call.enqueue(callback)
    }

    fun getPosts(callback:Callback<List<Post>>){
        val call = api.getPosts()
        call.enqueue(callback)
    }

}

interface MyApi{
    @GET("/posts")
    fun getPosts(): Call<List<Post>>
}