package com.tinle.emptyproject.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.api.ApiHandler
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val executor:AppExecutor,
        private val apiHandler:ApiHandler

):ViewModel(){
    private var viewData:MutableLiveData<List<Post>> = MutableLiveData()

    fun getData():LiveData<List<Post>>{
        executor.networkIO().execute {
            loadData()
        }
        return viewData
    }

    private fun loadData(){
        apiHandler.getPosts(object: Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                executor.mainThread().execute {
                    //viewData.value = emptyList()
                    setResult(emptyList())
                }
            }

            override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {
                var result:List<Post> = emptyList()

                if (response != null) {
                    if(response.body() != null) {
                        val posts = response.body()
                        result = posts!!
                        setResult(result)
                    }
                }
            }
        })
    }

    fun setResult(result:List<Post>) {
        executor.mainThread().execute {
            viewData.value = result
        }
    }
}