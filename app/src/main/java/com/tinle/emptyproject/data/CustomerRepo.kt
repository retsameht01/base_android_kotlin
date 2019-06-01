package com.tinle.emptyproject.data

import com.tinle.emptyproject.api.GposService
import com.tinle.emptyproject.core.AppExecutor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CustomerRepo @Inject constructor(
        private val customerDao:CustomerDao,
        private val appExecutor: AppExecutor,
        private val gposService: GposService

){
    init {
        appExecutor.networkIO().execute {
            gposService.getCustomers(object: Callback<List<RewardsMember>> {
                override fun onFailure(call: Call<List<RewardsMember>>, t: Throwable) {
                    print("Failed to get customers")
                }

                override fun onResponse(call: Call<List<RewardsMember>>, response: Response<List<RewardsMember>>) {
                    appExecutor.diskIO().execute {
                        response.body()?.let {
                            for (member in it){
                                member.SyncStatus = 1
                                customerDao.insertAll(member)}
                        }
                    }
                }
            })
        }
    }

    fun getCustomers():List<RewardsMember>{
        return customerDao.getAllCustomers()
    }

    fun getCustomer(phone:String):RewardsMember? {
        return customerDao.getCustomer(phone)
    }

    fun addCustomer(rewardsCustomer:RewardsMember){
        customerDao.insertAll(rewardsCustomer)
    }

    fun updateSyncStatus(phone:String, syncStatus:Int) {
        customerDao.updateSyncStatus(syncStatus, phone)
    }
}