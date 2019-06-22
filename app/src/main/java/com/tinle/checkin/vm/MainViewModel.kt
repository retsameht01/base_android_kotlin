package com.tinle.checkin.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tinle.checkin.api.GposService
import com.tinle.checkin.core.AppExecutor
import com.tinle.checkin.data.Post
import com.tinle.checkin.data.PostRepo
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val executor:AppExecutor,
        private val gposService:GposService

):ViewModel(){
    private var postRepo:PostRepo = PostRepo()
    private var posts:LiveData<List<Post>>? =  null
    init {
        if(posts == null){
            posts = postRepo.getPosts()
        }
    }

    fun getData():LiveData<List<Post>>{
        return posts!!
    }

}