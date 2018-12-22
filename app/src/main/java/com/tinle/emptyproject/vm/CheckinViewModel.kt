package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.api.GposService
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.EventBus
import com.tinle.emptyproject.data.Checkin
import com.tinle.emptyproject.data.CheckinDao
import com.tinle.emptyproject.data.RewardsMember
import com.tinle.emptyproject.data.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CheckinViewModel @Inject constructor(
        private val executor: AppExecutor,
        private val gposService: GposService,
        private val checkinDao: CheckinDao

):ViewModel() {
    private lateinit var checkInPhones: List<String>
    init {
        executor.diskIO().execute{
            checkInPhones = checkinDao.getAllPhones()
            println("init complete")
            //TODO AutoCompleteTextView Handle typing suggestions to users
        }
    }

    fun getExistingPhones():List<String> {
        return checkInPhones
    }

    fun checkIn(phone:String){
        val re = Regex("[^0-9]")
        val rawPhone = re.replace(phone, "")

        executor.networkIO().execute {
            gposService.getCustomerInfo(rawPhone,
                    object: Callback<RewardsMember> {
                        override fun onFailure(call: Call<RewardsMember>?, t: Throwable?) {
                            print("Result failed")
                        }

                        override fun onResponse(call: Call<RewardsMember>?, response: Response<RewardsMember>?) {
                            var custInfo =  response?.body()
                            if (custInfo != null) {
                                executor.diskIO().execute{
                                    checkinDao.insertAll(Checkin(getTimeStamp(), rawPhone))
                                }
                                SessionManager.setCustomer(custInfo)
                                EventBus.notify(AppEvent.CustomerRetrieved)
                            }
                        }
                    }
            )
        }
    }

    private fun getTimeStamp(): String {
        val simpleDateFormat = SimpleDateFormat("MM-dd-yyyy hh-mm-ss")
        val format = simpleDateFormat.format(Date())
        return format
    }

    fun isPhoneValid(phone:String):Boolean {
        val regex = Regex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}\$")
        return regex.matches(phone)
    }
}