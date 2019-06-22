package com.tinle.checkin.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tinle.checkin.data.RewardsMember
import com.tinle.checkin.data.Promotion
import com.tinle.checkin.data.SessionManager
import javax.inject.Inject

class CustomerRewardsVM @Inject constructor(
):ViewModel() {

    private var promos:MutableLiveData<List<Promotion>> = MutableLiveData()
    init {
        var promos = mutableListOf<Promotion>()
        promos.add(Promotion("id", "Specials 10% Off", "10"))
        promos.add(Promotion("id2", "Specials 15% Off", "15"))
        promos.add(Promotion("id3", "Specials 20% Off", "20"))
        promos.add(Promotion("id4", "$10 Off", "100"))
        this.promos.value = promos
    }

    fun getCustomerInfo():LiveData<RewardsMember>{
        return SessionManager.getCustomer()
    }

    fun getPromotions():LiveData<List<Promotion>>{
        return promos
    }

}