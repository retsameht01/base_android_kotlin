package com.tinle.emptyproject.data
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

object SessionManager:ISessionManager {
    private lateinit var custInfo:CustomerInfo
    private var liveCustomer:MutableLiveData<CustomerInfo> = MutableLiveData()

    override fun setCustomer(customerInfo: CustomerInfo) {
        custInfo = customerInfo
        liveCustomer.value = customerInfo
    }

    override fun getCustomer(): LiveData<CustomerInfo> {
        return liveCustomer
    }


}

interface ISessionManager{
    fun setCustomer(customerInfo: CustomerInfo)
    fun getCustomer():LiveData<CustomerInfo>
}