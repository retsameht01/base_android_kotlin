package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.telephony.PhoneNumberFormattingTextWatcher
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
import kotlinx.android.synthetic.main.activity_checkin.*

class CheckinFragment:BaseFragment() {
    lateinit var viewModel: CheckinViewModel
    lateinit var dialog: PasscodeDialog
    val clickListner = DialogInterface.OnClickListener { _, i ->
        if(dialog != null) {
            if(dialog.isValidPasscode()) {
                changeFragment(ManageRewardsFragment())
            }
        }
     }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.activity_checkin, container, false)
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(CheckinViewModel::class.java)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkinBtn.setOnClickListener {
            doCheckin()
        }

        settingsBtn.setOnClickListener {
            showPasswordDialog()
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
        checkinPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }

    override fun onResume() {
        val imm:InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(checkinPhone, InputMethodManager.SHOW_FORCED)
        checkinPhone.setText("")
        checkinPhone.requestFocus()
        super.onResume()
    }



    private fun doCheckin(){
        viewModel.checkIn(checkinPhone.text.toString())
    }

    private fun showPasswordDialog() {
        val fm: FragmentManager = activity!!.supportFragmentManager
        dialog  = PasscodeDialog.newInstance("Some Title", clickListner)
        dialog.show(fm, "fragment_edit_name")
    }

    override fun onBusEvent(event: AppEvent) {
        if (event == AppEvent.CustomerRetrieved) {
            changeFragment(RewardsFragment())
        }
    }
}