package com.tinle.emptyproject.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.api.GposService
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.RewardsMember
import javax.inject.Inject

class RewardsManagerVM @Inject constructor(
        private val executor: AppExecutor,
        private val gposService: GposService

        ):ViewModel() {
    private val data:MutableLiveData<List<RewardsMember>> = MutableLiveData()

    fun getAllCheckIns():LiveData<List<RewardsMember>>{
        data.postValue(getData())
        return data

    }


    private fun getData():List<RewardsMember>{
        var list:MutableList<RewardsMember> = mutableListOf()
        for(i in 0..4) {
            list.add(RewardsMember("member $i", "" +((i + 1) * 10)))
        }
        return list
    }
}