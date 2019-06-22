package com.tinle.checkin.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinle.checkin.R
import com.tinle.checkin.core.AppEvent
import com.tinle.checkin.vm.SignUpVM
import kotlinx.android.synthetic.main.fragment_signup.*
import android.app.DatePickerDialog
import com.tinle.checkin.vm.CheckinViewModel
import java.util.*
import java.text.SimpleDateFormat


class SignUpFragment:BaseFragment() {
    private lateinit var viewModel: SignUpVM
    private lateinit var checkInVM: CheckinViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(SignUpVM::class.java)
        checkInVM = ViewModelProviders.of(activity!!, vmFactory).get(CheckinViewModel::class.java)
        var view = inflater.inflate(R.layout.fragment_signup, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        bundle?.let {
            val phone = it.getString("Phone")
            signupPhone.setText(phone)
        }

        setToolbarVisibility(View.GONE)
        signupBtn.setOnClickListener{
            val inputError = viewModel.signUp(signupFirstName.text.toString(), signupLastName.text.toString(), signupPhone.text.toString(), signupEmail.text.toString(), signupDOB.text.toString(),
                    object: SignUpVM.SignupListener {
                        override fun onComplete(success: Boolean, msg: String) {
                            hideDialog()
                            if (success) {
                                showToast("Check-In Success")
                                changeFragment(CheckinFragment())
                            }
                            else {
                                showToast(msg)
                            }
                        }
                    }
            )

            if (inputError.isEmpty()) {
               showDialog("Signup", "Saving your account...")
            } else {
                showToast("$inputError")
            }
        }

        datePickerBtn.setOnClickListener {
            DatePickerDialog(this.context, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        backBtn.setOnClickListener {
            changeFragment(CheckinFragment())
        }
    }

    val myCalendar = Calendar.getInstance()

    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        signupDOB.setText(sdf.format(myCalendar.time))
    }

    val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
    }


    override fun onBusEvent(event: AppEvent) {
        //Don't do anything
    }

    override fun onPause() {
        super.onPause()
        hideDialog()
    }

}