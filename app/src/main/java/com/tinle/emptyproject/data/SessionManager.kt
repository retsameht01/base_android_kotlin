package com.tinle.emptyproject.data
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

object SessionManager:ISessionManager {
    private lateinit var custInfo:RewardsMember
    private var liveCustomer:MutableLiveData<RewardsMember> = MutableLiveData()

    override fun setCustomer(customerInfo: RewardsMember) {
        custInfo = customerInfo
        liveCustomer.value = customerInfo
    }

    override fun getCustomer(): LiveData<RewardsMember> {
        return liveCustomer
    }


}

interface ISessionManager{
    fun setCustomer(customerInfo: RewardsMember)
    fun getCustomer():LiveData<RewardsMember>
}