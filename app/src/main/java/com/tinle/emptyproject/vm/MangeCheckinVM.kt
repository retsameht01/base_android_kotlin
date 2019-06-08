package com.tinle.emptyproject.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.*
import javax.inject.Inject

class MangeCheckinVM @Inject constructor (
        private val checkinRepo: CheckinRepo,
        private val appExecutor: AppExecutor,
        private val customerRepo: CustomerRepo


) : ViewModel() {
    private var customerCheckinMap:HashMap<String, RewardsMember> = hashMapOf()
    private var checkinsData:MutableLiveData<List<CheckinViewData>> = MutableLiveData()
    private lateinit var selectedCheckin:CheckinViewData

    private fun loadCheckin() {
        appExecutor.diskIO().execute {
            val checkins = checkinRepo.getTodayCheckins()
            val checkinData = mutableListOf<CheckinViewData>()
            for (checkin in checkins) {
                val customer =  customerRepo.getCustomer(checkin.phone)
                if (customer != null) {
                    val checkinViewData = CheckinViewData(checkin.phone, customer.FirstName!!,
                            customer.LastName!!, customer.EmailAddress!!, checkin.checkinTime, "${customer.RewardPoints}")
                    checkinData.add(checkinViewData)
                    customerCheckinMap.put(checkin.phone, customer)
                }
            }
            checkinsData.postValue(checkinData)
        }
    }

    fun getCheckins():LiveData<List<CheckinViewData>> {
        loadCheckin();
        return checkinsData
    }

    fun setSelectedCheckin(checkin: CheckinViewData) {
        selectedCheckin = checkin
    }

    fun getSelectedCheckin():CheckinViewData {
        return selectedCheckin
    }

}