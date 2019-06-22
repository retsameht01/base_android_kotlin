package com.tinle.checkin.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinle.checkin.R
import com.tinle.checkin.core.AppEvent
import kotlinx.android.synthetic.main.fragment_countdown.*

class CountDownFragment:BaseFragment() {

    var count:Long = 10

    var countdown:CountDownTimer = object:CountDownTimer(count * 1000, 1000){
        override fun onFinish() {
            count = 10
            countdownTxt.text = "0"

        }

        override fun onTick(p0: Long) {
            countdownTxt.text = "$count"
            count--
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_countdown, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startBtn.setOnClickListener {
            countdown.start()
        }
    }


    override fun onBusEvent(event: AppEvent) {

    }

}