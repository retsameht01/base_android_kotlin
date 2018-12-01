package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.PhoneNumberFormattingTextWatcher
import com.tinle.emptyproject.R
import dagger.android.AndroidInjection
import android.view.inputmethod.InputMethodManager
import com.tinle.emptyproject.vm.CheckinViewModel
import kotlinx.android.synthetic.main.activity_checkin.*
import javax.inject.Inject


class CheckInActivity:AppCompatActivity() {
    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    lateinit var vieModel: CheckinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        vieModel = ViewModelProviders.of(this, vmFactory).get(CheckinViewModel::class.java)
        setContentView(R.layout.activity_checkin)
        checkinPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        checkinPhone.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        checkinBtn.setOnClickListener{
            vieModel.checkIn(checkinPhone.text.toString())
        }
    }

}