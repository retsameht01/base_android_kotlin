package com.tinle.checkin.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class PostRepo {
    //private lateinit var apiHandler:GposService
    init{
        //apiHandler = GposService(null)
    }

    fun getPosts(): LiveData<List<Post>> {
        val data:MutableLiveData<List<Post>> = MutableLiveData()
        /*
        apiHandler.getPosts(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {
                var resultData = response!!.body()
                if(resultData == null) {
                    data.value = emptyList()
                }
                else {
                    data.value = resultData;
                }
            }
        }) */
        return data
    }

}