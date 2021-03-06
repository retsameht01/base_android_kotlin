package com.tinle.checkin.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.fantasticsoft.gposlinklib.PostLinkHandler
import com.tinle.checkin.MainActivity
import com.tinle.checkin.R
import com.tinle.checkin.core.AppEvent
import com.tinle.checkin.data.CommSetings
import com.tinle.checkin.vm.SettingsVM
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment:BaseFragment() {
    override fun onBusEvent(event: AppEvent) {

    }

    private lateinit var posHandler: PostLinkHandler

    private lateinit var viewModel:SettingsVM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(SettingsVM::class.java)
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        posHandler = (activity as MainActivity).getPosHandler()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarVisibility(View.GONE)
        apiText.setText(viewModel.getAPIValue())
        terminalIp.setText(viewModel.getCommIP())

        val commAdapter = ArrayAdapter.createFromResource(activity,
                R.array.comm_types, R.layout.payment_type_spinner_text)

        commAdapter.setDropDownViewResource(R.layout.payment_type_spinner_text_dropdown)
        commType.adapter = commAdapter

        saveBtn.setOnClickListener {
            viewModel.saveCommIP(terminalIp.text.toString());
            val com = CommSetings()
            com.CommType = commType.selectedItem.toString()
            com.CommIP = terminalIp.text.toString();
            com.CommPort = ""
            com.Host = terminalHost.text.toString()
            if (com.CommType.equals("USB")) {
                com.Host = "FullIntegration";
            }

            val json:String = Gson().toJson(com, CommSetings::class.java)
            val intent = Intent()
            intent.action ="com.gpos.paxrequest"
            intent.putExtra("RequestType", "COMM")
            intent.putExtra("Data", json)
            startActivityForResult(intent, 112)
        }

        testSocketBtn.setOnClickListener {
            viewModel.doSocketConnection(terminalIp.text.toString(), terminalHost.text.toString())
        }

        viewModel.getServerString().observe(this, Observer<String>{
                showToast("result $it")
            }
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            viewModel.saveCommIP(terminalIp.text.toString())
            val result = data.getStringExtra(Intent.EXTRA_TEXT)
            changeFragment(CheckinFragment())
            showToast("set comm success : $result")
        }
    }

}