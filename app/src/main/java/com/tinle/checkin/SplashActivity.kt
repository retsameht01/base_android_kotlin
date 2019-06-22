package com.tinle.checkin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class SplashActivity: AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private var handler = Handler()

    lateinit var progressDialog: ProgressDialog

    //TODO setup a viewmodel to load the customer data to local database
    //TODO use rxjava, rxandroid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
        setContentView(R.layout.activity_splash)
        showDialog("Sync Data", "Loading data....")
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 3500)
    }

    override fun onStop() {
        super.onStop()
        hideDialog()
    }

    fun showDialog(title:String, msg:String) {
        progressDialog.setTitle(title)
        progressDialog.setMessage(msg)
        progressDialog.show()
    }

    fun hideDialog() {
        progressDialog.dismiss()
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}