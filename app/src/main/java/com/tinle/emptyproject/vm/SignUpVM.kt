package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.api.GposService
import com.tinle.emptyproject.core.AppExecutor
import javax.inject.Inject

class SignUpVM @Inject constructor(
        private val executor:AppExecutor,
        private val gposService:GposService

):ViewModel() {

    fun signup(name:String, phone:String, email:String) {

    }
}