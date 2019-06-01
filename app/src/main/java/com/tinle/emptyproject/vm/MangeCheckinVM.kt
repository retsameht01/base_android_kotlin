package com.tinle.emptyproject.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.*
import javax.inject.Inject

class MangeCheckinVM @Inject constructor(
        checkinRepo: CheckinRepo,
        appExecutor: AppExecutor,
        customerRepo: CustomerRepo


) : ViewModel() {

    //private var allCheckin: MutableLiveData<List<Checkin>> = MutableLiveData()
    private var customers:HashMap<String, RewardsMember> = hashMapOf()
    private var checkinsData:MutableLiveData<List<CheckinViewData>> = MutableLiveData()

    init {
        appExecutor.diskIO().execute{
            val checkins = checkinRepo.getCheckins()
            val checkinData = mutableListOf<CheckinViewData>()
            for (checkin in checkins) {
                val customer =  customerRepo.getCustomer(checkin.phone)
                if(customer != null) {
                    val checkinViewData = CheckinViewData(checkin.phone, customer.FirstName!!,
                            customer.LastName!!, customer.EmailAddress!!, checkin.checkinTime)
                    checkinData.add(checkinViewData)
                    customers.put(checkin.phone, customer)
                }
            }

            checkinsData.postValue(checkinData)
            //allCheckin.postValue(checkins)
        }
    }

    fun getCheckins():LiveData<List<CheckinViewData>> {
        return checkinsData
    }




}