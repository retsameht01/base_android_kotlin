package com.tinle.emptyproject.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.api.GposService
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.Post
import com.tinle.emptyproject.data.PostRepo
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