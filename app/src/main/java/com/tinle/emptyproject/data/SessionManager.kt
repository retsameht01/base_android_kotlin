package com.tinle.emptyproject.data

object SessionManager:ISessionManager {
    private lateinit var custInfo:CustomerInfo

    override fun setCustomer(customerInfo: CustomerInfo) {
        custInfo = customerInfo
    }

    override fun getCustomer(): CustomerInfo {
        return custInfo
    }
}

interface ISessionManager{
    fun setCustomer(customerInfo: CustomerInfo)
    fun getCustomer():CustomerInfo
}