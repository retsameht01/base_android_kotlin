package com.tinle.checkin.view

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable

class PhoneTextListener: PhoneNumberFormattingTextWatcher() {

     override fun afterTextChanged(s: Editable) {
        super.afterTextChanged(s)

    }
}