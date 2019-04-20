package com.tinle.emptyproject.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.Checkin
import com.tinle.emptyproject.data.CheckinRepo
import javax.inject.Inject

class MangeCheckinVM @Inject constructor(
        checkinRepo: CheckinRepo,
        appExecutor: AppExecutor

) : ViewModel() {

    var allCheckin: MutableLiveData<List<Checkin>> = MutableLiveData()

    init {
        appExecutor.diskIO().execute{
            val checkins = checkinRepo.getCheckins()
            allCheckin.postValue(checkins)
        }
    }




}