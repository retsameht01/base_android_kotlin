package com.tinle.emptyproject.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.api.GposService
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.DateUtil
import com.tinle.emptyproject.data.RewardsMember
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class RewardsManagerVM @Inject constructor(
        private val executor: AppExecutor,
        private val gposService: GposService,
        private val dateUtil:DateUtil

        ):ViewModel() {
    init {
        gposService.getCustomers(object: Callback<List<RewardsMember>>{
            override fun onFailure(call: Call<List<RewardsMember>>, t: Throwable) {
                print("Failed to get customers")
            }

            override fun onResponse(call: Call<List<RewardsMember>>, response: Response<List<RewardsMember>>) {
                try {
                    data.postValue(response.body())
                }
                catch (ex:Exception) {
                    print("Exception")
                }
            }
        })
    }
    private val data:MutableLiveData<List<RewardsMember>> = MutableLiveData()

    fun getAllCheckIns():LiveData<List<RewardsMember>>{
        return data
    }

    fun getLastVisitInfo(visitDate:String):String {
        if(visitDate == null || visitDate.isEmpty()){
            return "Last Visit: NA"
        }
        //"LastPurchase": "2018-08-17T11:03:01.653",
        var date = visitDate.substring(0, 10)

        var daysDiff = dateUtil.findDaysDiff(date)
        return "Last Visit: $date ($daysDiff days)"
    }
}