package com.tinle.checkin.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.EditText
import com.tinle.checkin.R


class CheckoutDialog:DialogFragment() {

    private lateinit var clickListener:DialogInterface.OnClickListener
    private lateinit var ticketText:EditText
    companion object {
        fun newInstance(title:String, listener:DialogInterface.OnClickListener):CheckoutDialog{
            val dialog = CheckoutDialog()
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
        val title = arguments!!.getString("title", "Set Checkout Value");
        val builder = AlertDialog.Builder(activity)
                .setPositiveButton("Ok", clickListener)
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i -> this.dismiss() })
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.checkout_dialog, null)
        ticketText = view.findViewById(R.id.ticketValue)
        builder.setView(view)
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
           dialog.window.setLayout(600,300)
        }
    }


}