package com.tinle.checkin.view

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
import com.tinle.checkin.R
import com.tinle.checkin.core.AppEvent
import com.tinle.checkin.vm.CheckinViewModel
import kotlinx.android.synthetic.main.fragment_checkin.*
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.InputType
import android.widget.EditText

class CheckinFragment:BaseFragment(), KeyboardView.OnKeyboardActionListener {
    lateinit var viewModel: CheckinViewModel
        lateinit var mKeyboard: Keyboard
        lateinit var phoneText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_checkin, container, false)
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(CheckinViewModel::class.java)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()
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

        todayDate.text = viewModel.getDate()
        checkinPhone.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        checkinPhone.setTextIsSelectable(false)
        mKeyboard = Keyboard(this.context, R.xml.num_keypad)
        val keyboardView:KeyboardView = keyboardview
        keyboardView.keyboard = mKeyboard
        keyboardView.isPreviewEnabled = false
        keyboardView.setOnKeyboardActionListener(this)
        phoneText = checkinPhone
    }

    override fun onResume() {
        val imm:InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(checkinPhone, InputMethodManager.SHOW_FORCED)
        checkinPhone.setText("")
        //checkinPhone.requestFocus()
        super.onResume()
    }

    override fun onPause(){
        super.onPause()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(checkinPhone.windowToken, 0)
        hideDialog()
    }

    private fun doCheckin(){
        if (!viewModel.isPhoneValid(checkinPhone.text.toString())) {
            showToast("Invalid Phone. Please try again!")
            return
        }
        try {
            showDialog("Check In", "Checking in...")
            viewModel.checkIn(checkinPhone.text.toString())
        }
        catch (ex:Exception) {
            println("Unable to perform check-in ")
            hideDialog()
        }
    }

    override fun onBusEvent(event: AppEvent) {
        when (event) {
            AppEvent.CustomerRetrieved -> {
                activity?.runOnUiThread {
                    showToast("Check in success")
                    clearPhoneInput()
                    hideDialog()
                }
            }
            AppEvent.CustomerNotFound -> {
                val bundle = Bundle()
                bundle.putString("Phone", viewModel.getCurrentPhone())
                changeFragment(SignUpFragment(), bundle)
            }
            AppEvent.AlreadyCheckedIn -> {
                activity?.runOnUiThread {
                    showToast("Already checked in")
                    clearPhoneInput()
                    hideDialog()
                }
            }
        }
    }

    //Custom keyboard codes
    val CodeDelete = -5 // Keyboard.KEYCODE_DELETE
    val CodeCancel = -3 // Keyboard.KEYCODE_CANCEL
    val CodePrev = 55000
    val CodeAllLeft = 55001
    val CodeLeft = 55002
    val CodeRight = 55003
    val CodeAllRight = 55004
    val CodeNext = 55005
    val CodeClear = 55006
    val CodeEnter = 10

    override fun swipeRight() {

    }

    override fun onPress(primaryCode: Int) {
        //val focusCurrent = activity?.getWindow()?.getCurrentFocus()
        //if (focusCurrent == null || focusCurrent!!.javaClass != EditText::class.java) return
        val edittext = phoneText
        val editable = edittext.text
        val start = edittext.selectionStart
        // Handle key
        if (primaryCode === CodeCancel) {
            //hideCustomKeyboard()
        } else if (primaryCode === CodeDelete) {
            if (editable != null && start > 0) editable.delete(start - 1, start)
        } else if (primaryCode === CodeClear) {
            editable?.clear()
        } else if (primaryCode === CodeLeft) {
            if (start > 0) edittext.setSelection(start - 1)
        } else if (primaryCode === CodeRight) {
            if (start < edittext.length()) edittext.setSelection(start + 1)
        } else if (primaryCode === CodeAllLeft) {
            edittext.setSelection(0)
        } else if (primaryCode === CodeAllRight) {
            edittext.setSelection(edittext.length())
        } else if (primaryCode === CodePrev) {
            val focusNew = edittext.focusSearch(View.FOCUS_RIGHT)
            focusNew?.requestFocus()
        } else if (primaryCode === CodeEnter) {
            doCheckin()
        } else if (primaryCode === CodeNext) {
            val focusNew = edittext.focusSearch(View.FOCUS_RIGHT)
            focusNew?.requestFocus()
        } else {// Insert character
            val string =  Character.toChars(primaryCode).toString()
            val ch = Character.toChars(primaryCode)[0]
            editable!!.insert(start, "$ch")
        }
    }

    override fun onRelease(p0: Int) {

    }

    override fun swipeLeft() {

    }

    override fun swipeUp() {

    }

    override fun swipeDown() {

    }

    override fun onKey(primaryCode: Int, p1: IntArray?) {
    }

    override fun onText(p0: CharSequence?) {

    }

    private fun hideKeyboard() {
        try {
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val currentFocusedView = activity?.getCurrentFocus()
            if (currentFocusedView != null) {
                inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clearPhoneInput() {
        checkinPhone.text.clear()
    }

    inner class PhoneTextListener: PhoneNumberFormattingTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            val phone = s.toString()
        }
    }
}