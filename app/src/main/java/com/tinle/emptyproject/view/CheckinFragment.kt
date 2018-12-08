package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                changeFragment(SettingsFragment())
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
            viewModel.checkIn(checkinPhone.text.toString())
        }

        settingsBtn.setOnClickListener {
            showPasswordDialog()
        }
        checkinPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
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