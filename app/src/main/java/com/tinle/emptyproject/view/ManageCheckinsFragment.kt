package com.tinle.emptyproject.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tinle.emptyproject.R
import com.tinle.emptyproject.data.CheckinViewData
import com.tinle.emptyproject.vm.MangeCheckinVM
import kotlinx.android.synthetic.main.fragment_manage_checkins.*


class ManageCheckinsFragment: BaseFragment() {
    private lateinit var viewModel: MangeCheckinVM
    lateinit var dialog: CheckoutDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setToolbarVisibility(View.GONE)
        var view = inflater.inflate(R.layout.fragment_manage_checkins, container, false)
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(MangeCheckinVM::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkinList.layoutManager = LinearLayoutManager(activity)
        showLoadingDialog()

        viewModel.getCheckins().observe(this, Observer<List<CheckinViewData>>{
           if( it != null) {
               checkinCountTxt.text = String.format(getString(R.string.checkin_count), "${it.size}")
               checkinList.adapter = CheckinAdapter(it)
           }
            hideDialog()

            //TODO Rethink the check in flow and update the app
            //TODO Also, for future features: add voice check in
            //TODO Add customizable features and skinning the app.

        })

        backBtn.setOnClickListener {
            changeFragment(CheckinFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideDialog()
    }


    inner class CheckinAdapter(val checkins:List<CheckinViewData>): RecyclerView.Adapter<CheckinViewHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CheckinViewHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.checkin_row, p0,false)
            return CheckinViewHolder(view)
        }

        override fun getItemCount(): Int {
            return checkins.size
        }

        override fun onBindViewHolder(holder: CheckinViewHolder, pos: Int) {
            val checkin = checkins[pos]
            holder.checkinName.text = "${checkin.lastName} ${checkin.firstName} ${checkin.phone}"
            holder.checkinTime.text = checkin.timeStamp
        }
    }

    inner class CheckinViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val checkinName: TextView = view.findViewById(R.id.checkinName)
        val checkinTime: TextView = view.findViewById(R.id.checkinTime)
    }

}