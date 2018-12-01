package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.PhoneNumberFormattingTextWatcher
import com.tinle.emptyproject.R
import dagger.android.AndroidInjection
import android.view.inputmethod.InputMethodManager
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.core.BusListener
import com.tinle.emptyproject.core.EventBus
import com.tinle.emptyproject.vm.CheckinViewModel
import kotlinx.android.synthetic.main.activity_checkin.*
import javax.inject.Inject


class CheckInActivity:AppCompatActivity(), BusListener {
    override fun onBusEvent(event: AppEvent) {
        if (event == AppEvent.CustomerRetrieved) {
            startActivity(Intent(this, CustomerRewardsActivity::class.java))
        }
    }

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    lateinit var vieModel: CheckinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        EventBus.addListener(this)
        vieModel = ViewModelProviders.of(this, vmFactory).get(CheckinViewModel::class.java)
        setContentView(R.layout.activity_checkin)
        checkinPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        checkinBtn.setOnClickListener{
            vieModel.checkIn(checkinPhone.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        val imm:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(checkinPhone, InputMethodManager.SHOW_FORCED)
        checkinPhone.setText("")
        checkinPhone.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        val imm:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(checkinPhone.windowToken, 0)
    }

}