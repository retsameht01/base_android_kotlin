package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.vm.CheckinViewModel
import kotlinx.android.synthetic.main.fragment_checkin.*

class CheckinFragment:BaseFragment() {
        lateinit var viewModel: CheckinViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_checkin, container, false)
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(CheckinViewModel::class.java)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarVisibility(View.VISIBLE)
        checkinBtn.setOnClickListener {
            doCheckin()
        }

        checkinPhone.setOnEditorActionListener(object:TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                val result = actionId and EditorInfo.IME_MASK_ACTION
                if(result == EditorInfo.IME_ACTION_DONE) {
                    doCheckin()
                }
                return true
            }
        })
        checkinPhone.addTextChangedListener(PhoneTextListener())
        signiupLink.setOnClickListener{
            changeFragment(SignUpFragment())
        }
    }

    override fun onResume() {
        val imm:InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(checkinPhone, InputMethodManager.SHOW_FORCED)
        checkinPhone.setText("")
        checkinPhone.requestFocus()
        super.onResume()
    }

    override fun onPause(){
        super.onPause()
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(checkinPhone.windowToken, 0)
    }

    private fun doCheckin(){
        viewModel.checkIn(checkinPhone.text.toString())
    }

    override fun onBusEvent(event: AppEvent) {
        if (event == AppEvent.CustomerRetrieved) {
            changeFragment(RewardsFragment())
        } else if(event == AppEvent.CustomerNotFound) {
            doCheckin()
        }
    }

    inner class PhoneTextListener: PhoneNumberFormattingTextWatcher() {

        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            val phone = s.toString()
            if(viewModel.isPhoneValid(phone)){
                viewModel.checkIn(phone)
            }
        }
    }
}