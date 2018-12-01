package com.tinle.emptyproject.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.data.CustomerInfo
import com.tinle.emptyproject.data.SessionManager
import javax.inject.Inject

class CustomerRewardsVM @Inject constructor(
):ViewModel() {

    fun getCustomerInfo():LiveData<CustomerInfo>{
        return SessionManager.getCustomer()
    }

}