package com.tinle.checkin.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.EditText
import com.tinle.checkin.R


class PasscodeDialog:DialogFragment() {

    lateinit var clickListener:DialogInterface.OnClickListener
    private val Passcode = "143222"
    lateinit var passCodeTxt:EditText
    companion object {
        fun newInstance(title:String, listener:DialogInterface.OnClickListener):PasscodeDialog{
            val dialog = PasscodeDialog()
            dialog.clickListener = listener
            val args = Bundle()
            args.putString("title", title)
            dialog.arguments = args
            return dialog
        }
    }


    /*
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.password_dialog, container, false)
        view.findViewById<Button>(R.id.okBtn).setOnClickListener { clickListener }
		return inflater.inflate(R.layout.password_dialog, container);
	}
	*/

    /*

	override fun onViewCreated(view: View, savedInstanceState:Bundle? ) {
		// Get field from view
		//mEditText = (EditText) view.findViewById(R.id.txt_your_name);
		// Fetch arguments from bundle and set title
		val title = arguments!!.getString("title", "Enter Name");
		getCheckoutDialog().setTitle(title);
		// Show soft keyboard automatically and request focus to field
		//mEditText.requestFocus();

		getCheckoutDialog().getWindow().setSoftInputMode(
		    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        super.onViewCreated(view, savedInstanceState);
	}
	*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments!!.getString("title", "Enter Code");
        val builder = AlertDialog.Builder(activity).setPositiveButton("Ok", clickListener)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.password_dialog, null)
        passCodeTxt = view.findViewById(R.id.passcodeTxt)
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
        val input =  passCodeTxt.text.toString()
        return input.equals(Passcode, true)
    }

}