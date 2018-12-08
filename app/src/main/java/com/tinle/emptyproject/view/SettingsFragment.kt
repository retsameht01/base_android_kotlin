package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.vm.SettingsVM
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment:BaseFragment() {
    override fun onBusEvent(event: AppEvent) {

    }

    private lateinit var viewModel:SettingsVM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(SettingsVM::class.java)
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        apiText.setText(viewModel.getAPIValue())
        saveBtn.setOnClickListener {
            viewModel.saveAPI(apiText.text.toString().trim())
            changeFragment(CheckinFragment())
        }
    }

}