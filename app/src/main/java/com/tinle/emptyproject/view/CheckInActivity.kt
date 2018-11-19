package com.tinle.emptyproject.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.PhoneNumberFormattingTextWatcher
import com.tinle.emptyproject.R
import dagger.android.AndroidInjection
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_checkin.*


class CheckInActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_checkin)
        checkinPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        checkinPhone.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

}