package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.vm.SignUpVM
import kotlinx.android.synthetic.main.fragment_signup.*


class SignUpFragment:BaseFragment() {


    private lateinit var viewModel: SignUpVM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(SignUpVM::class.java)
        var view = inflater.inflate(R.layout.fragment_signup, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarVisibility(View.GONE)
        signupBtn.setOnClickListener{
            viewModel.signup(signupFirstName.text.toString(), signupPhone.text.toString(), signupEmail.text.toString())
        }

        backBtn.setOnClickListener {
            changeFragment(CheckinFragment())
        }
    }


    override fun onBusEvent(event: AppEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}