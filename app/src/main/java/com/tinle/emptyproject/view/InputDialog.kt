package com.tinle.emptyproject.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.tinle.emptyproject.R
import faranjit.currency.edittext.CurrencyEditText


class InputDialog:DialogFragment() {

    lateinit var clickListener:DialogInterface.OnClickListener
    private val Passcode = "143222"
    lateinit var tipText: CurrencyEditText
    private var tipAmt = ""
    companion object {
        fun newInstance(title:String, listener:DialogInterface.OnClickListener):InputDialog{
            val dialog = InputDialog()
            dialog.clickListener = listener
            val args = Bundle()
            args.putString("title", title)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments!!.getString("title", "Enter Tip");
        val builder = AlertDialog.Builder(activity).setPositiveButton("Ok", clickListener)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.input_dialog, null)
        tipText = view.findViewById(R.id.tipInput)
        builder.setView(view)
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
           dialog.window.setLayout(400,250)
        }
    }

    fun isValidPasscode():Boolean{
        val input =  tipText.text.toString()
        return input.equals(Passcode, true)
    }

    fun getInputText():Double{
        return tipText.currencyDouble
    }

}