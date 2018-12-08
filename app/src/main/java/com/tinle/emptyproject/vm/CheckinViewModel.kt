package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.api.ApiHandler
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.EventBus
import com.tinle.emptyproject.data.CustomerInfo
import com.tinle.emptyproject.data.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CheckinViewModel @Inject constructor(
        private val executor: AppExecutor,
        private val apiHandler: ApiHandler

):ViewModel() {

    fun checkIn(phone:String){
        val re = Regex("[^0-9]")
        val numberRefined = re.replace(phone, "")
        executor.networkIO().execute {
            apiHandler.getCustomerInfo(numberRefined,
                    object: Callback<CustomerInfo> {
                        override fun onFailure(call: Call<CustomerInfo>?, t: Throwable?) {
                            print("Result failed")
                        }

                        override fun onResponse(call: Call<CustomerInfo>?, response: Response<CustomerInfo>?) {
                            var custInfo =  response?.body()
                            if (custInfo != null) {
                                SessionManager.setCustomer(custInfo)
                                EventBus.notify(AppEvent.CustomerRetrieved)
                            }
                        }
                    }
            )
        }
    }
}