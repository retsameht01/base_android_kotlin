package com.tinle.checkin.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.checkin.core.AppExecutor
import com.tinle.checkin.data.*
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
                customer?.let {
                    val checkinViewData = CheckinViewData(checkin.phone, "${it.FirstName}",
                            "${it.LastName}", "${it.EmailAddress}", checkin.checkinTime, "${it.RewardPoints}", it.RewardPointRate!!)
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