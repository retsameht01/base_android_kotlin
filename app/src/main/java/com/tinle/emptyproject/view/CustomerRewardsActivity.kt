package com.tinle.emptyproject.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.data.CustomerInfo
import com.tinle.emptyproject.vm.CustomerRewardsVM
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_customer_rewards.*
import javax.inject.Inject

class CustomerRewardsActivity:AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    lateinit var vieModel: CustomerRewardsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_customer_rewards)
        vieModel = ViewModelProviders.of(this, vmFactory).get(CustomerRewardsVM::class.java)
        vieModel.getCustomerInfo().observe(this, Observer<CustomerInfo>{
            welcomeText.text = "Welcome, ${it!!.FirstName}"
            rewardPoints.text = "${it!!.RewardPoints} pts"
        })
    }
}