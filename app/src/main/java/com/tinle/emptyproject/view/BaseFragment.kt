package com.tinle.emptyproject.view

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.core.BusListener
import com.tinle.emptyproject.core.EventBus
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import javax.inject.Inject
import android.widget.EditText
import android.view.LayoutInflater
import android.view.View


abstract class BaseFragment:Fragment(), BusListener {
    @Inject
    lateinit var vmFactory:ViewModelProvider.Factory

    lateinit var progressDialog:ProgressDialog

    override fun onAttach(context: Context){
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(false)
        super.onAttach(context)
        EventBus.addListener(this)
        progressDialog = ProgressDialog(this.context)
        setDefaultDialogTexts();
        progressDialog.setCancelable(false) // disable dismiss by tapping outside of the dialog
    }

    private fun setDefaultDialogTexts() {
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Wait while loading...")
    }

    fun changeFragment(target:BaseFragment) {
        try {
            val trans =  activity!!.supportFragmentManager.beginTransaction()
            trans.replace(R.id.fragment_container,target)
            trans.commit()
        }
        catch (ex:Exception) {
            println("Error switching fragment ${ex.message}")
        }
    }

    fun changeFragment(target:BaseFragment, bundle:Bundle) {
        try {
            target.arguments = bundle
            val trans =  activity!!.supportFragmentManager.beginTransaction()
            trans.replace(R.id.fragment_container,target)
            trans.commit()
        }
        catch (ex:Exception) {
            println("Error switching fragment ${ex.message}")
        }
    }

    fun showConfirmDialog(msg:String, title:String, okClick:DialogInterface.OnClickListener) {
        val builder = AlertDialog.Builder(this.activity!!)

        // Set the alert dialog title
        builder.setTitle(title)

        // Display a message on alert dialog
        builder.setMessage(msg)
        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES", okClick)


        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){dialog,which ->
            dialog.dismiss()
        }


        // Display a neutral button on alert dialog
        /*
        builder.setNeutralButton("Cancel"){_,_ ->

        } */

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()
        // Display the alert dialog on app interface
        dialog.show()

    }

    fun setToolbarVisibility(visValue:Int) {
        try {
            (activity as MainActivity).toolbar.visibility = visValue
        }
        catch (e:Exception) {

        }
    }

    fun showLoadingDialog(){
        setDefaultDialogTexts()
        progressDialog.show()
    }

    fun showDialog(title:String, msg:String) {
        progressDialog.setTitle(title)
        progressDialog.setMessage(msg)
        progressDialog.show()
    }

    fun hideDialog(){
        progressDialog.dismiss()
    }

    override fun onBusEvent(event: AppEvent) {
        //
    }

    fun showToast(msg:String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showPasswordDialog(){
        val layoutInflaterAndroid = LayoutInflater.from(this.context)
        val mView = layoutInflaterAndroid.inflate(R.layout.password_dialog, null)

        val alertDialogBuilderUserInput = AlertDialog.Builder(this.context!!)
        alertDialogBuilderUserInput.setView(mView)

        val userInputDialogEditText = mView.findViewById(R.id.passcodeTxt) as EditText
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Send") { dialogBox, id ->
                    showToast("Send button click " + userInputDialogEditText.text.toString())
                }

                .setNegativeButton("Cancel"
                ) { dialogBox, id -> dialogBox.cancel() }

        val alertDialogAndroid = alertDialogBuilderUserInput.create()
        alertDialogAndroid.show()
    }

}