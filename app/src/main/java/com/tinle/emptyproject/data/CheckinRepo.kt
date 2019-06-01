package com.tinle.emptyproject.data

import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.DateUtil
import javax.inject.Inject

class CheckinRepo @Inject constructor(
        val appExecutor: AppExecutor,
        val checkinDao: CheckinDao,
        val dateUtil:DateUtil

) {

    fun doCheckin(phone:String) {
        val timeStamp = dateUtil.getUTCDateTimestamp()
        val time = dateUtil.getCurrentTime()
        val todayDate = dateUtil.getCurrentDate()
        val checkin = Checkin(time, todayDate, timeStamp, phone)
        appExecutor.networkIO().execute{
            checkinDao.insertAll(checkin)
        }
    }

    fun checkout(phone: String) {
        val timeStamp = dateUtil.getUTCDateTimestamp()
        appExecutor.networkIO().execute{
            checkinDao.setCheckout(timeStamp, phone)
        }
    }

    fun getCheckins():List<Checkin> {
        return checkinDao.getAllCheckins()
    }

    fun getTodayCheckins():List<Checkin> {
        return checkinDao.getAllCheckins()
    }
}