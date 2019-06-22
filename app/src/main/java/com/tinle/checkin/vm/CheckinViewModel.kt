package com.tinle.checkin.vm

import android.arch.lifecycle.ViewModel
import com.tinle.checkin.api.GposService
import com.tinle.checkin.core.AppEvent
import com.tinle.checkin.core.AppExecutor
import com.tinle.checkin.core.DateUtil
import com.tinle.checkin.core.EventBus
import com.tinle.checkin.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CheckinViewModel @Inject constructor(
        private val executor: AppExecutor,
        private val gposService: GposService,
        private val checkinDao: CheckinDao,
        private val dateUtil:DateUtil,
        private val customerRepo: CustomerRepo


):ViewModel() {
    private lateinit var checkInPhones: List<String>
    private var currentPhone = ""
    private var checkinProgress = false
    init {
        executor.diskIO().execute{
            checkInPhones = checkinDao.getAllPhones()
        }
    }

    fun getExistingPhones():List<String> {
        return checkInPhones
    }

    fun getCurrentPhone():String {
        return currentPhone
    }

    fun getDate():String {
        val date = dateUtil.getBasicDate()
        return date
    }

    fun checkIn(phone:String) {
        if (checkinProgress ) {
           return
        }
        executor.networkIO().execute {
            val rawPhone = removePhoneFormatting(phone)
            val checkins = checkinDao.getOpenCheckin(rawPhone)
            if (checkins.isEmpty()) {
                checkinProgress = true
                currentPhone = rawPhone
                gposService.getCustomerInfo(rawPhone,
                        object: Callback<RewardsMember> {
                            override fun onFailure(call: Call<RewardsMember>?, t: Throwable?) {
                                print("Result failed")
                                EventBus.notify(AppEvent.CustomerNotFound)
                                checkinProgress = false
                            }
                            override fun onResponse(call: Call<RewardsMember>?, response: Response<RewardsMember>?) {
                                processCustomerInfoResponse(response, rawPhone)
                            }
                        }
                )
            } else {
                checkinProgress = false
                EventBus.notify(AppEvent.AlreadyCheckedIn)
            }
        }
    }

    private  fun removePhoneFormatting(phone:String):String {
        val re = Regex("[^0-9]")
        val rawPhone = re.replace(phone, "")
        return rawPhone
    }

    private fun processCustomerInfoResponse(response: Response<RewardsMember>?, phone:String) {
        try {
            var custInfo =  response?.body()
            if (custInfo != null) {
                executor.diskIO().execute {
                    checkinDao.insertAll(Checkin(dateUtil.getCurrentTime(), dateUtil.getCurrentDate(), getTimeStamp(), phone))
                    customerRepo.addCustomer(RewardsMember(0, custInfo.FirstName, custInfo.LastName, custInfo.Phone, custInfo.EmailAddress,
                            custInfo.RewardPointRate, custInfo.RewardPoints, custInfo.LastPurchase, custInfo.birthDate,1
                    ))
                }

                SessionManager.setCustomer(custInfo)
                //Do online checkin
                gposService.signIn(buildSignInRequestData(custInfo), object:Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        print("Failed")
                    }
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        println("response")
                    }
                })
                EventBus.notify(AppEvent.CustomerRetrieved)
                checkinProgress = false
            }
        } catch (ex:Exception) {
            print("Error " + ex.message);
            EventBus.notify(AppEvent.GenericError)
            checkinProgress = false
        }
    }

    private fun buildSignInRequestData(cust : RewardsMember):SignInDTO {
        return SignInDTO(
                cust.Id,
                dateUtil.getUTCDateTimestamp(),
                false,
                "",
                cust.Id,
                cust.Phone,
                cust.FirstName?:"",
                cust.LastName?:"",
                "None"
        )
    }

    private fun getTimeStamp(): String {
        val simpleDateFormat = SimpleDateFormat("MM-dd-yyyy hh-mm-ss")
        val format = simpleDateFormat.format(Date())
        return format
    }

    fun isPhoneValid(phone:String):Boolean {
        val rawPhone = removePhoneFormatting(phone)
        val regex = Regex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}\$")
        //TODO Don't make it too hard for customer to check-in. Handle this validation better.!!!
        return true//regex.matches(rawPhone)
    }
}