package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fantasticsoft.gposlinklib.PostLinkHandler
import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.data.CommSetings
import com.tinle.emptyproject.vm.SettingsVM
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
        creditCardIpText.setText(viewModel.getCommIP())
        saveBtn.setOnClickListener {
            /*
            viewModel.saveAPI(apiText.text.toString().trim())
            viewModel.saveCommIP(creditCardIpText.text.toString())
            posHandler.SaveCommSettings(creditCardIpText.text.toString(), "", "")
            changeFragment(CheckinFragment())

            */

            viewModel.saveCommIP(creditCardIpText.text.toString());
            val com = CommSetings()
            com.CommType = "HTTP"
            com.CommIP = creditCardIpText.text.toString();
            com.CommPort = ""

            val json:String = Gson().toJson(com, CommSetings::class.java)

            val intent = Intent()
            intent.action ="com.gpos.paxrequest"
            intent.putExtra("RequestType", "COMM")
            intent.putExtra("Data", json)
            startActivityForResult(intent, 112)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            viewModel.saveCommIP(creditCardIpText.text.toString())
            val result = data.getStringExtra(Intent.EXTRA_TEXT)
            print("on result " + result)
            changeFragment(CheckinFragment())
        } else{
            Toast.makeText(this.context, "Request failed:", Toast.LENGTH_LONG).show()
        }

    }

}